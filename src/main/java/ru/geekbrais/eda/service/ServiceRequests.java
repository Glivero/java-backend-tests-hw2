package ru.geekbrais.eda.service;

import io.restassured.specification.RequestSpecification;
import lombok.experimental.UtilityClass;
import ru.geekbrais.eda.utils.Endpoints;
import ru.geekbrais.eda.dto.PostImageResponse;

import static io.restassured.RestAssured.given;

@UtilityClass
public class ServiceRequests {

    public static PostImageResponse uploadCommonImage(RequestSpecification spec) {
        return given()
                .spec(spec)
                .when()
                .post(Endpoints.POST_IMAGE_REQUEST)
                .prettyPeek()
                .then()
                .extract()
                .body()
                .as(PostImageResponse.class);
    }
}
