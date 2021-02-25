package ru.geekbrains.eda.Post;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.geekbrains.eda.Service.ServiceTests;

import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.Objects;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

public class PostWrongFormatTest extends ServiceTests {

    String encodedImage;
    String uploadedImageHashCode;

    @BeforeEach
    void setUp() {
        byte[] fileContent = getFileContentInBase64();
        encodedImage = Base64.getEncoder().encodeToString(fileContent);
    }

    @Test
    void uploadLargeFileTest() {
        uploadedImageHashCode = given()
                .headers("Authorization", token)
                .multiPart("image", encodedImage)
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

    private byte[] getFileContentInBase64() {
        ClassLoader classLoader = getClass().getClassLoader();
        File inputFile = new File(Objects.requireNonNull(classLoader.getResource("Test.rar")).getFile());
        byte[] fileContent = new byte[0];
        try {
            fileContent =   FileUtils.readFileToByteArray(inputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileContent;
    }
}