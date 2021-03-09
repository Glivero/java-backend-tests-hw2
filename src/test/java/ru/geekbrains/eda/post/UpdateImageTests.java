package ru.geekbrains.eda.post;

import org.junit.jupiter.api.Test;
import ru.geekbrains.eda.Service.ServiceTests;

import java.io.File;
import java.util.Map;
import java.util.Objects;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class UpdateImageTests extends ServiceTests {

    @Test
    void updateNormalImageTest() {
        ClassLoader classLoader = getClass().getClassLoader();
        File inputFile = new File(Objects.requireNonNull(classLoader.getResource("kosmonavt.jpg")).getFile());
        Map data = given()
                .headers("Authorization", token)
                .multiPart("image", inputFile)
                .expect()
                .body("success", is(true))
                .body("data.id", is(notNullValue()))
                .body("data.type", is("image/jpeg"))
                .when()
                .post("/image")
                .prettyPeek()
                .then()
                .statusCode(200)
                .extract()
                .response()
                .jsonPath()
                .get("data");
        String deleteHash = (String) data.get("deletehash");
        String id = (String) data.get("id");

        given()
                .headers(headers)
                .param("title", "TEST")
                .expect()
                .body("success", is(true))
                .when()
                .post("/image/{id}", id)
                .prettyPeek()
                .then()
                .statusCode(200);

        given()
                .headers(headers)
                .expect()
                .body("data.title", is("TEST"))
                .when()
                .get("image/{id}", id)
                .then()
                .statusCode(200);

        given()
                .headers("Authorization", token)
                .when()
                .delete("image/{deleteHash}", deleteHash)
                .prettyPeek()
                .then()
                .statusCode(200);
    }
}
