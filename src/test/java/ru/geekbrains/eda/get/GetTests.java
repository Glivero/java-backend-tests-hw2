package ru.geekbrains.eda.get;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import ru.geekbrains.eda.service.ServiceTests;
import ru.geekbrais.eda.Endpoints;
import ru.geekbrais.eda.dto.GetAccountResponse;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@Slf4j
public class GetTests extends ServiceTests {

    @Test
    void getAccountInfoPositiveTest(){
        given()
                .spec(requestSpecification)
                .when()
                .get(Endpoints.GET_ACCOUNT_REQUEST, username)
                .prettyPeek()
                .then();
    }

    @Test
    void getAccountInfoPositiveWithObjectPTest(){
        GetAccountResponse response = given()
                .spec(requestSpecification)
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
                .spec(requestSpecification)
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