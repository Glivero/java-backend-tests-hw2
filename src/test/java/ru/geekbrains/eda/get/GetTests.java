package ru.geekbrains.eda.get;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import ru.geekbrains.eda.service.ServiceTests;
import ru.geekbrais.eda.Endpoints;
import ru.geekbrais.eda.dto.GetAccountResponse;
import ru.geekbrais.eda.dto.GetImageResponse;
import ru.geekbrais.eda.dto.GetNegativeResponse;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@Slf4j
public class GetTests extends ServiceTests {

    @Test
    void getAccountInfoPositiveTest(){
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
    }

    @Test
    void getAccountInfoPositiveWithManyObjectsTest(){
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
        assertThat(response.getData().getCreated(),equalTo(1608706699));
    }




    @Test
    void getAccountInfoNoAuthTest(){
        GetNegativeResponse response = given()
                .spec(requestSpecificationWithoutAuth)
                .when()
                .get(Endpoints.GET_ACCOUNT_REQUEST, "")
                .prettyPeek()
                .then()
                .extract()
                .body()
                .as(GetNegativeResponse.class);
        log.info(response.getStatus().toString());
        assertThat(response.getStatus(),equalTo(403));
        assertThat(response.getSuccess(),equalTo(false));
        assertThat(response.getData().error,equalTo("The access token was not found."));
    }







    @Test
    void getImageBrokenEndpointTest() {
        GetImageResponse response = given()
                .spec(requestSpecification)
                .when()
                .get(Endpoints.GET_IMAGE_REQUEST_BROKEN_ENDPOINT)
                .prettyPeek()
                .then()
                .extract()
                .body()
                .as(GetImageResponse.class);
        log.info(response.getStatus().toString());
        assertThat(response.getStatus(),equalTo(400));
    }

    @Test
    public void getImagePositiveTest() {
        GetImageResponse response = given()
                .spec(requestSpecification)
                .expect()
                .when()
                .get(Endpoints.GET_IMAGE_REQUEST, imageId)
                .prettyPeek()
                .as(GetImageResponse.class);
        log.info(response.getStatus().toString());
        assertThat(response.getStatus(),equalTo(200));
        assertThat(response.getData().id,equalTo("SOUdj4z"));
        assertThat(response.getData().type,equalTo("image/jpeg"));
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