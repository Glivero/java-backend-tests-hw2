package ru.geekbrains.eda.Get;

import io.restassured.RestAssured;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import ru.geekbrains.eda.Service.ServiceTests;
import ru.geekbrais.eda.dto.Endpoints;
import ru.geekbrais.eda.dto.GetAccountResponse;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@Slf4j
public class GetAccountTests extends ServiceTests {

//    @Test
//    void getAccountInfoNegativePTest(){
//        RestAssured.when()
//                .get("/account/Glivero/{username}", username)
//                .prettyPeek();
//    }

    @Test
    void getAccountInfoPositiveTest(){
        given()
                .spec(requestSpecification)
                .log()
                .all()
                .when()
                .get("/account/Glivero/{username}", username)
                .prettyPeek()
                .then();
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

    @Test
    void getAccountInfoPositiveWithObjectPTest(){
        GetAccountResponse response = given()
                .when()
                .get(Endpoints.GET_ACCOUNT_REQUEST, username)
                .prettyPeek()
                .then()
                .extract()
                .body()
                .as(GetAccountResponse.class);
        log.info(response.getStatus().toString());
        assertThat(response.getStatus(),equalTo(200));
        assertThat(response.getData().getId(),equalTo(142829187));
        assertThat(response.getData().getUrl(),equalTo("Glivero"));
    }

}