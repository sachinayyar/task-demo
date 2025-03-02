package com.redhat.developers.tektongitjibkn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyController {
    @Autowired
    private Environment environment;

    final String hostname = System.getenv().getOrDefault("HOSTNAME", "unknown");

    String greeting;

    private int count = 0; // simple counter to see lifecycle

    @GetMapping("/namaste")    
    public String index() {
        greeting = environment.getProperty("GREETING","Namaste");
        greeting = greeting + " From sachin Shivaji Ayyar" + count + " on " + hostname + "\n";
        System.out.println(greeting);
        return greeting;
    }

    @GetMapping("/hello")    
    public String hello() {
        return "hello";
    }    
}
