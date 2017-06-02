package com.example.basicapi;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by anthonyikeda on 2/6/17.
 */
@RestController
public class DemoController {

    @GetMapping
    public ResponseEntity<Demo> getDemo() {

        Demo demo = new Demo();
        demo.setMessage("Welcome to the jungle!");
        return ResponseEntity.ok(demo);
    }
}
