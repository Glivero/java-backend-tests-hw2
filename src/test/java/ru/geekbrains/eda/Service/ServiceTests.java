package ru.geekbrains.eda.Service;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ServiceTests {

    protected static Properties prop = new Properties();
    protected static String token;
    protected static String username;
    protected static String imageId;
    protected static String imageUrl;
    protected static Map<String, String> headers = new HashMap<>();

    @BeforeAll
    static void beforeAll() {
        loadProperties();

        token = prop.getProperty("token");
        headers.put("Authorization", token);
        username = prop.getProperty("username");
        imageId = prop.getProperty("image.id");
        imageUrl = prop.getProperty("image.url");

        RestAssured.baseURI = prop.getProperty("base.url");
        RestAssured.filters(new AllureRestAssured());
    }

    private static void loadProperties() {
        try {
            prop.load(new FileInputStream("src/test/resources/application.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
