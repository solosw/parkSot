package com.example.parking.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;

import java.io.*;

@Component
public class FileUtils {

    @Autowired
    ResourcePathProvider resourcePathProvider;


    public  String readJsonFile(String fileName) {

       // System.out.println(resourcePathProvider.getResourcesAbsolutePath(""));
        Resource resource = new ClassPathResource(fileName);
        try {
            InputStream inputStream = resource.getInputStream();
            byte[] bdata = FileCopyUtils.copyToByteArray(inputStream);
            return new String(bdata);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void writeJsonFile(String fileName, String jsonData) {
        try {
            // 获取 resources 文件夹的绝对路径
            ClassLoader classLoader = getClass().getClassLoader();
            File file = new File(classLoader.getResource(fileName).getFile());

            try (Writer writer = new FileWriter(file)) {
                writer.write(jsonData);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (NullPointerException e) {
            System.err.println("File not found: " + fileName);
        }
    }

}
