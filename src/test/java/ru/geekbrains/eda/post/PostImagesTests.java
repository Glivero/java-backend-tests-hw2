package ru.geekbrains.eda.post;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.geekbrains.eda.Service.ServiceTests;

import java.io.File;
import java.util.Base64;
import java.util.Objects;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class PostImagesTests extends ServiceTests {

    String encodedImage;
    String uploadedImageHashCode;

    @BeforeEach
    void setUp() {
        byte[] fileContent = getFileContentInBase64();
        encodedImage = Base64.getEncoder().encodeToString(fileContent);
    }

    @Test
    void uploadImageBase64Test() {
        uploadedImageHashCode = given()
                .headers("Authorization", token)
                .multiPart("image", encodedImage)
                .expect()
                .body("success", is(true))
                .body("data.id", is(notNullValue()))
                .when()
                .post("/image")
                .prettyPeek()
                .then()
                .statusCode(200)
                .extract()
                .response()
                .jsonPath()
                .getString("data.deletehash");
    }

    @Test
    void uploadImageUsingUrlTest() {
        uploadedImageHashCode = given()
                .headers("Authorization", ServiceTests.token)
                .multiPart("image", imageUrl)
                .expect()
                .body("success", is(true))
                .body("data.id", is(notNullValue()))
                .when()
                .post("/image")
                .prettyPeek()
                .then()
                .extract()
                .response()
                .jsonPath()
                .getString("data.deletehash");
    }

    @Test
    void uploadSmallImageTest() {
        ClassLoader classLoader = getClass().getClassLoader();
        File inputFile = new File(Objects.requireNonNull(classLoader.getResource("Small.jpg")).getFile());
        uploadedImageHashCode = given()
                .headers("Authorization", token)
                .multiPart("image", inputFile)
                .expect()
                .body("success", is(true))
                .body("data.id", is(notNullValue()))
                .when()
                .post("/image")
                .prettyPeek()
                .then()
                .statusCode(200)
                .extract()
                .response()
                .jsonPath()
                .getString("data.deletehash");
    }

    @Test
    void uploadLargeFileTest() {
        ClassLoader classLoader = getClass().getClassLoader();
        File inputFile = new File(Objects.requireNonNull(classLoader.getResource("Large.png")).getFile());
        uploadedImageHashCode = given()
                .headers("Authorization", token)
                .multiPart("image", inputFile)
                .expect()
                .body("success", is(false))
                .when()
                .post("/image")
                .prettyPeek()
                .then()
                .statusCode(400)
                .extract()
                .response()
                .jsonPath()
                .getString("data.deletehash");
    }

    @AfterEach
    void tearDown() {
        try {

            given()
                    .headers("Authorization", token)
                    .when()
                    .delete("account/{username}/image/{deleteHash}", username, uploadedImageHashCode)
                    .prettyPeek()
                    .then()
                    .statusCode(200);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}