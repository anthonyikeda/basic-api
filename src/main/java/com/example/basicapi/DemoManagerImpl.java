package com.example.basicapi;

import org.springframework.stereotype.Component;

/**
 * Created by anthonyikeda on 2/6/17.
 */
@Component
public class DemoManagerImpl implements DemoManager {

    @Override
    public Demo getDemo() {
        Demo demo = new Demo();
        demo.setMessage("Welcome to the jungle!");
        return demo;
    }
}
