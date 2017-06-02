package com.example.basicapi;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class DemoManagerTest {

    @Autowired
    private DemoManager demoManager;

    @Test
    public void testGetDemo() {
        Demo demo = demoManager.getDemo();
        Assert.assertNotNull(demo);
    }
}
