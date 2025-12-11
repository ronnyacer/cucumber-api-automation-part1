package com.example.api.hooks;

import io.cucumber.java.Before;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Hooks {
    private static boolean initialized = false;

    @Before(order = 0)
    public void setuo() throws IOException {
        if (initialized) {
            return;
        }
        initialized = true;
        Properties props = new Properties();
        try (InputStream input = Hooks.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                throw new RuntimeException("Config Properties not found");
            }
            props.load(input);
        } catch (Exception e) {
            throw new RuntimeException("Failed to Load Config");
        }
        String baseUrl = props.getProperty("api.base.url");
        String basePath = props.getProperty("api.base.path", "");
        String apiKey = props.getProperty("api.key");
        RestAssured.baseURI = baseUrl;
        RestAssured.basePath = basePath;
        RequestSpecification spec = new RequestSpecBuilder().setBaseUri(baseUrl).setBasePath(basePath).addHeader("x-api-key", apiKey).setContentType(ContentType.JSON).build();
        RestAssured.requestSpecification = spec;
    }
}
