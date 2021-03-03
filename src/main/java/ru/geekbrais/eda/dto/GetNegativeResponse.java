package ru.geekbrais.eda.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

@JsonPropertyOrder({
        "error",
        "request",
        "method"
})

public class GetNegativeResponse extends ServiceResponse<GetNegativeResponse.AccountData>{

    @Data
    public static class AccountData {

        @JsonProperty("error")
        public String error;
        @JsonProperty("request")
        public String request;
        @JsonProperty("method")
        public String method;
    }
}

