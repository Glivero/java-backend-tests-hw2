package ru.geekbrains.eda.get;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import ru.geekbrains.eda.Service.ServiceTests;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

public class GetAccountTests extends ServiceTests {

    @Test
    void getAccountInfoPositiveTest(){

        given()
                .log()
                .all()
                .headers("Authorization", token)
                .when()
                .get("/account/Glivero/{username}", username)
                .prettyPeek()
                .then()
                .statusCode(200);
    }

    @Test
    void getAccountInfoPositiveWithManyChecksTest(){

        given()
                .headers("Authorization", token)
                .expect()
                .body(CoreMatchers.containsString(username))
                .body("success", is(true))
                .body("data.id", is(142829187))
                .when()
                .get("/account/Glivero/{username}", username)
                .prettyPeek()
                .then()
                .statusCode(200);
    }
}
