package ru.geekbrais.eda.service;

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
    public AnyData data;
    @JsonProperty("success")
    public Boolean success;
    @JsonProperty("status")
    public Integer status;

}
