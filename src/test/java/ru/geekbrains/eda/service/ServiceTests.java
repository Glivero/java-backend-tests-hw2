package ru.geekbrains.eda.service;

import com.github.javafaker.Faker;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.builder.MultiPartSpecBuilder;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.MultiPartSpecification;
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

    public static Properties prop = new Properties();
    public static String token;
    public static String username;
    public static String imageId;
    public static String imageUrl;
    public static Map<String, String> headers = new HashMap<>();
    public static ResponseSpecification responseSpecification;
    public static RequestSpecification requestSpecification;
    public static RequestSpecification requestSpecificationWithoutAuth;
    public static MultiPartSpecification multiPartSpec;
    public static RequestSpecification uploadReqSpec;
    Faker faker = new Faker();

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
                .build();

        requestSpecification = new RequestSpecBuilder()
                .addHeader("Authorization", token)
                .setAccept(ContentType.ANY)
                .build();

        requestSpecificationWithoutAuth = new RequestSpecBuilder()
                .addHeader("Authorization", "")
                .setAccept(ContentType.ANY)
                .build();

//        RestAssured.responseSpecification = responseSpecification;
//        RestAssured.requestSpecification = requestSpecification;

    }

    private static void loadProperties() {
        try {
            prop.load(new FileInputStream("src/test/resources/application.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void preparePostSpecs(Object content) {
        multiPartSpec = new MultiPartSpecBuilder(content)
                .controlName("image")
                .build();
        uploadReqSpec = new RequestSpecBuilder()
                .addHeader("Authorization", token)
                .setAccept(ContentType.ANY)
                .build()
                .multiPart(multiPartSpec)
                .formParam("title", faker.chuckNorris().fact())
                .formParam("description", faker.harryPotter().quote());
    }
}
