package ru.geekbrains.eda.get;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import ru.geekbrains.eda.service.ServiceTests;
import ru.geekbrais.eda.Endpoints;
import ru.geekbrais.eda.dto.GetAccountResponse;
import ru.geekbrais.eda.dto.GetImageResponse;
import ru.geekbrais.eda.dto.NegativeResponse;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
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
    void getAccountInfoNoTokenTest(){
        NegativeResponse response = given()
                .spec(requestSpecificationWithoutAuth)
                .when()
                .get(Endpoints.GET_ACCOUNT_REQUEST, "")
                .prettyPeek()
                .then()
                .extract()
                .body()
                .as(NegativeResponse.class);
        log.info(response.getStatus().toString());
        assertThat(response.getStatus(),equalTo(403));
        assertThat(response.getSuccess(),equalTo(false));
        assertThat(response.getData().error,equalTo("The access token was not found."));
    }

    @Test
    void getAccountInfoNoAuthTest(){
        NegativeResponse response = given()
                .when()
                .get(Endpoints.GET_ACCOUNT_REQUEST, "")
                .prettyPeek()
                .then()
                .extract()
                .body()
                .as(NegativeResponse.class);
        log.info(response.getStatus().toString());
        assertThat(response.getStatus(),equalTo(401));
        assertThat(response.getSuccess(),equalTo(false));
        assertThat(response.getData().error,equalTo("Authentication required"));
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
    void getImageBrokenEndpointTest() {
        NegativeResponse response = given()
                .spec(requestSpecification)
                .when()
                .get(Endpoints.GET_IMAGE_REQUEST_BROKEN_ENDPOINT)
                .prettyPeek()
                .then()
                .extract()
                .body()
                .as(NegativeResponse.class);
        log.info(response.getStatus().toString());
        assertThat(response.getStatus(),equalTo(400));
        assertThat(response.getSuccess(),equalTo(false));
        assertThat(response.getData().error,equalTo("An image ID is required for a GET request to /image"));
    }

    @Test
    public void getImageNoAuthorizationTest() {
        NegativeResponse response = given()
                .expect()
                .when()
                .get(Endpoints.GET_IMAGE_REQUEST, imageId)
                .prettyPeek()
                .as(NegativeResponse.class);
        log.info(response.getStatus().toString());
        assertThat(response.getStatus(),equalTo(401));
        assertThat(response.getSuccess(),equalTo(false));
        assertThat(response.getData().error,equalTo("Authentication required"));
    }

}