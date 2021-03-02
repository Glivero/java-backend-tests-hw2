package ru.geekbrais.eda.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonPropertyOrder({
        "data",
        "success",
        "status"
})

@NoArgsConstructor
@Data
public class ServiceResponse<AnyData> {

    @JsonProperty("data")
    private AnyData data;
    @JsonProperty("success")
    private Boolean success;
    @JsonProperty("status")
    private Integer status;

}
