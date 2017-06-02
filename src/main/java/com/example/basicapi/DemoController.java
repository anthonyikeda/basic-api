package com.example.basicapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by anthonyikeda on 2/6/17.
 */
@RestController
public class DemoController {

    @Autowired
    private DemoManager demoManager;

    @GetMapping
    public ResponseEntity<Demo> getDemo() {
        Demo demo = demoManager.getDemo();
        return ResponseEntity.ok(demo);
    }
}
