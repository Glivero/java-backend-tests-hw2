package ru.geekbrains.eda.post;

import org.junit.jupiter.api.Test;
import ru.geekbrains.eda.Service.ServiceTests;

import java.io.File;
import java.util.Objects;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class DeleteImageTests extends ServiceTests {

    String uploadedImageHashCode;

    @Test
    void deleteImageTest() {
        ClassLoader classLoader = getClass().getClassLoader();
        File inputFile = new File(Objects.requireNonNull(classLoader.getResource("Small.jpg")).getFile());
        uploadedImageHashCode = given()
                .headers("Authorization", token)
                .multiPart("image", inputFile)
                .expect()
                .body("success", is(true))
                .body("data.type", is("image/jpeg"))
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

        given()
                .headers("Authorization", token)
                .when()
                .delete("account/{username}/image/{deleteHash}", username, uploadedImageHashCode)
                .prettyPeek()
                .then()
                .statusCode(200);
    }

    @Test
    void deleteImageNoAuthTest() {
        ClassLoader classLoader = getClass().getClassLoader();
        File inputFile = new File(Objects.requireNonNull(classLoader.getResource("Small.jpg")).getFile());
        uploadedImageHashCode = given()
                .headers("Authorization", token)
                .multiPart("image", inputFile)
                .expect()
                .body("success", is(true))
                .body("data.type", is("image/jpeg"))
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

        given()
                .when()
                .delete("account/{username}/image/{deleteHash}", username, uploadedImageHashCode)
                .prettyPeek()
                .then()
                .statusCode(401);
    }

    @Test
    void deleteBrokenEndpointTest() {
        ClassLoader classLoader = getClass().getClassLoader();
        File inputFile = new File(Objects.requireNonNull(classLoader.getResource("Small.jpg")).getFile());
        uploadedImageHashCode = given()
                .headers("Authorization", token)
                .multiPart("image", inputFile)
                .expect()
                .body("success", is(true))
                .body("data.type", is("image/jpeg"))
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

        given()
                .headers("Authorization", token)
                .when()
                .delete("account/{username}/imageBROKEN}", username)
                .prettyPeek()
                .then()
                .statusCode(400);
    }

}
