package com.example.parking.utils;

import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ResourcePathProvider {

    private final ResourceLoader resourceLoader;

    public ResourcePathProvider(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    public String getResourcesAbsolutePath(String replacePath) {
        Resource resource = resourceLoader.getResource("classpath:");
        try {
            return rewritePath(resource.getURL().getPath(),replacePath);
        } catch (IOException e) {
            // Handle the exception
            e.printStackTrace();
            return null;
        }
    }

    String rewritePath(String originalPath,String targetFolder) {
        String replacement = "";

        if (originalPath.contains(targetFolder)) {
            return originalPath.replace(targetFolder, replacement)+"/src/main/resources";
        } else {
            return originalPath;
        }
    }
}

