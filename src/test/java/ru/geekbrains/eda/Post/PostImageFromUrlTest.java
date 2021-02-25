package ru.geekbrains.eda.Post;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import ru.geekbrains.eda.Service.ServiceTests;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class PostImageFromUrlTest extends ServiceTests {


    String uploadedImageHashCode;

    @Test
    void uploadFileUsingUrlTest() {
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

    @AfterEach
    void tearDown() {
        given()
                .headers("Authorization", token)
                .when()
                .delete("account/{username}/image/{deleteHash}", username, uploadedImageHashCode)
                .prettyPeek()
                .then()
                .statusCode(200);
    }

}
