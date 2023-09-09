package com.example.restfulwebservice.controller;

import com.example.restfulwebservice.exception.UserNotFoundException;
import com.example.restfulwebservice.pojo.Post;
import com.example.restfulwebservice.pojo.User;
import com.example.restfulwebservice.repository.PostRepository;
import com.example.restfulwebservice.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/jpa")
public class UserJpaController {

    private final UserRepository userRepository;

    private final PostRepository postRepository;

    public UserJpaController(UserRepository userRepository, PostRepository postRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    @GetMapping("/users")
    public List<User> retrieveAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/users/{userId}")
    public EntityModel<User> retrieveUserById(@PathVariable int userId) {
        Optional<User> resUser = userRepository.findById(userId);

        if (resUser.isEmpty()) {
            throw new UserNotFoundException("id: " + userId);
        }

        EntityModel<User> entityModel = EntityModel.of(resUser.get());
        WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).retrieveAllUsers());
        entityModel.add(link.withRel("all-users"));

        return entityModel;

    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        User responseUser = userRepository.save(user);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(responseUser.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/users/{userId}")
    public void deleteUserById(@PathVariable int userId) {
        userRepository.deleteById(userId);
    }

    @GetMapping("/users/{userId}/posts")
    public List<Post> retrievePostForUser(@PathVariable int userId) {
        Optional<User> resUser = userRepository.findById(userId);
        if (resUser.isEmpty()) {
            throw new UserNotFoundException("id: " + userId);
        }
        return resUser.get().getPosts();
    }

    @PostMapping("/users/{userId}/posts")
    public ResponseEntity<Object> createPostForUser(@PathVariable int userId, @Valid @RequestBody Post post) {
        Optional<User> resUser = userRepository.findById(userId);
        if (resUser.isEmpty()) {
            throw new UserNotFoundException("id: " + userId);
        }

        post.setUser(resUser.get());
        Post savedPost = postRepository.save(post);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedPost.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }
}
