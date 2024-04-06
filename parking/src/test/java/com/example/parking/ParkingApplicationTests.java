package com.example.parking;

import com.example.parking.utils.JWTUtils;
import com.example.parking.utils.ResourcePathProvider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;

@SpringBootTest
class ParkingApplicationTests {

    @Autowired
    JWTUtils jwtUtils;

    @Autowired
    ResourcePathProvider resourcePathProvider;
    @Test
    void contextLoads() {
        System.out.println(resourcePathProvider.getResourcesAbsolutePath("/target/test-classes/"));
    }
    @Test
    void contextLoads2() {
        jwtUtils.verify("  ");

    }
}
