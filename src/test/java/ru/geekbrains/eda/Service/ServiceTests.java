package ru.geekbrains.eda.Service;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.hamcrest.Matchers;
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
    protected static ResponseSpecification responseSpecification = null;
    protected static RequestSpecification requestSpecification;

    @BeforeAll
    static void beforeAll() {
        loadProperties();

        token = prop.getProperty("token");
        headers.put("Authorization", token);
        username = prop.getProperty("username");
        imageId = prop.getProperty("image.id");
        imageUrl = prop.getProperty("image.url");

        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.baseURI = prop.getProperty("base.url");
        RestAssured.filters(new AllureRestAssured());

        responseSpecification = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectStatusLine("HTTP/1.1 200 OK")
                .expectContentType(ContentType.JSON)
                .expectResponseTime(Matchers.lessThan(5000L))
                .expectHeader("Access-Control-Allow-Credentials","true")
                .build();

        requestSpecification = new RequestSpecBuilder()
                .addHeader("Authorization", token)
                .setAccept(ContentType.ANY)
                .setContentType(ContentType.ANY)
                .build();

        RestAssured.responseSpecification = responseSpecification;
        RestAssured.requestSpecification = requestSpecification;

    }

    private static void loadProperties() {
        try {
            prop.load(new FileInputStream("src/test/resources/application.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
