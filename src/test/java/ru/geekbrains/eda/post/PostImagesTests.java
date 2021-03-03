package ru.geekbrains.eda.post;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import ru.geekbrains.eda.service.ServiceTests;
import ru.geekbrais.eda.utils.Endpoints;
import ru.geekbrais.eda.dto.NegativeResponse;
import ru.geekbrais.eda.dto.PostImageResponse;
import ru.geekbrais.eda.service.ServiceRequests;
import ru.geekbrais.eda.utils.FileEncodingUtils;
import ru.geekbrais.eda.utils.Images;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class PostImagesTests extends ServiceTests {

    String uploadedImageHashCode;

    @Test
    void uploadPositiveImageTest() {
        preparePostSpecs(FileEncodingUtils.getFileContent(Images.POSITIVE.path));
        PostImageResponse response = ServiceRequests.uploadCommonImage(uploadReqSpec);
        uploadedImageHashCode = response.getData().getDeletehash();
        assertThat(response.getData().getType(), equalTo("image/jpeg"));
        assertThat(response.getSuccess(), equalTo(true));
        assertThat(response.getStatus(), equalTo(200));
    }

    @Test
    void uploadSmallImageTest() {
        preparePostSpecs(FileEncodingUtils.getFileContent(Images.SMALL.path));
        PostImageResponse response = ServiceRequests.uploadCommonImage(uploadReqSpec);
        uploadedImageHashCode = response.getData().getDeletehash();
        assertThat(response.getData().getType(), equalTo("image/jpeg"));
        assertThat(response.getSuccess(), equalTo(true));
        assertThat(response.getStatus(), equalTo(200));
    }

    @Test
    void uploadFromUrlImageTest() {
        preparePostSpecs(Images.FROM_URL.path);
        PostImageResponse response = ServiceRequests.uploadCommonImage(uploadReqSpec);
        uploadedImageHashCode = response.getData().getDeletehash();
        assertThat(response.getData().getType(), equalTo("image/jpeg"));
        assertThat(response.getSuccess(), equalTo(true));
        assertThat(response.getStatus(), equalTo(200));
    }

    @Test
    void uploadBigSizeImageTest() {
        preparePostSpecs(FileEncodingUtils.getFileContent(Images.BIG_SIZE.path));
        NegativeResponse response = given()
                .spec(uploadReqSpec)
                .when()
                .post(Endpoints.POST_IMAGE_REQUEST)
                .prettyPeek()
                .then()
                .extract()
                .body()
                .as(NegativeResponse.class);
        assertThat(response.getSuccess(),equalTo(false));
        assertThat(response.getStatus(),equalTo(400));
        assertThat(response.getData().error,equalTo("File is over the size limit"));
    }

    @AfterEach
    void tearDown() {
        if (uploadedImageHashCode != null) {
            given()
                    .spec(requestSpecification)
                    .when()
                    .delete(Endpoints.DELETE_IMAGE_REQUEST, uploadedImageHashCode)
                    .prettyPeek();
        }
    }
}
