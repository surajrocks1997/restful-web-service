package com.example.restfulwebservice.controller;

import com.example.restfulwebservice.pojo.Name;
import com.example.restfulwebservice.pojo.PersonV1;
import com.example.restfulwebservice.pojo.PersonV2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VersioningPersonController {

    @GetMapping("/v1/person")
    public PersonV1 getFirstVersionOfPerson() {
        return new PersonV1("Bob Charlie");
    }

    @GetMapping("/v2/person")
    public PersonV2 getSecondVersionOfPerson() {
        return new PersonV2(new Name("Bob", "Charlie"));
    }

    @GetMapping(path = "/person", params = "version=1")
    public PersonV1 getFirstPersonRequestParameter() {
        return new PersonV1("Bob Charlie");
    }

    @GetMapping(path="person", params = "version=2")
    public PersonV2 getSecondPersonRequestParameter() {
        return new PersonV2(new Name("Bob", "Charlie"));
    }

    @GetMapping(path = "/person", headers = "X-API-VERSION=1")
    public PersonV1 getFirstPersonRequestHeader() {
        return new PersonV1("Bob Charlie");
    }

    @GetMapping(path = "/person", headers = "X-API-VERSION=2")
    public PersonV2 getSecondPersonRequestHeader() {
        return new PersonV2(new Name("Bob", "Charlie"));
    }

    @GetMapping(path = "/person", produces = "application/company.app-v1+json")
    public PersonV1 getFirstPersonAcceptHeader() {
        return new PersonV1("Bob Charlie");
    }

    @GetMapping(path = "/person", produces = "application/company.app-v2+json")
    public PersonV2 getSecondPersonAcceptHeader() {
        return new PersonV2(new Name("Bob", "Charlie"));
    }
}
