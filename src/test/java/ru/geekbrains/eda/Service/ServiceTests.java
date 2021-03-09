package ru.geekbrains.eda.Service;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.BeforeAll;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
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

    public byte[] getFileContentInBase64() {
        ClassLoader classLoader = getClass().getClassLoader();
        File inputFile = new File(Objects.requireNonNull(classLoader.getResource("kosmonavt.jpg")).getFile());
        byte[] fileContent = new byte[0];
        try {
            fileContent =   FileUtils.readFileToByteArray(inputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileContent;
    }

    private static void loadProperties() {
        try {
            prop.load(new FileInputStream("src/test/resources/application.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
