package ru.geekbrains.eda.Get;

import org.junit.jupiter.api.Test;
import ru.geekbrains.eda.Service.ServiceTests;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

public class GetImageTests extends ServiceTests{

    @Test
    public void getImageBrokenEndpointTest() {
        given()
                .headers("Authorization", ServiceTests.token)
                .expect()
                .body("success", is(false))
                .when()
                .get("/image")
                .prettyPeek()
                .then()
                .statusCode(400);
    }

    @Test
    public void getImageTest() {
        given()
                .headers("Authorization", ServiceTests.token)
                .expect()
                .body("success", is(true))
                .when()
                .get("/image/{id}", imageId)
                .prettyPeek()
                .then()
                .statusCode(200);
    }

    @Test
    public void getImageNoAuthorizationTest() {
        given()
                .expect()
                .body("success", is(false))
                .when()
                .get("/image/SOUdj4z")
                .prettyPeek()
                .then()
                .statusCode(401);
    }

}
