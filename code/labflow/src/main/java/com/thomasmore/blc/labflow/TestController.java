package com.thomasmore.blc.labflow;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
public class TestController {
    private Map<String, Test> db = new HashMap<>() {{
        put("1", new Test("1", "eerste object"));
    }};

    @GetMapping("/")
    public String hello() {
        return "Hello BLC, this is LabFlow APP";
    }

    @GetMapping("/test")
    public Collection<Test> test() {
        return db.values();
    }

    @GetMapping("test/{id}")
    public Test getTests(@PathVariable String id) {
        Test test = db.get(id);
        if (test == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return test;
    }
}
