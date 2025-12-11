package com.example.api.utils;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class ResourceUtils {

    public static String readResourceAsString(String path) {
        InputStream is = ResourceUtils.class.getClassLoader().getResourceAsStream(path);
        if (is == null) {
            throw new RuntimeException("Resource not found: " + path);
        }
        try (Scanner scanner = new Scanner(is, StandardCharsets.UTF_8)) {
            return scanner.useDelimiter("\\A").next();
        }
    }
}
