package ru.geekbrais.eda.steps;

import io.restassured.specification.RequestSpecification;
import lombok.experimental.UtilityClass;
import ru.geekbrais.eda.Endpoints;
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
                .statusCode(200)
                .extract()
                .body()
                .as(PostImageResponse.class);
    }
}
