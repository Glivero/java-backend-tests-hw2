package ru.geekbrais.eda.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import ru.geekbrais.eda.service.ServiceResponse;

@Data
public class NegativeResponse extends ServiceResponse<NegativeResponse.NegativeData> {

    @Data
    public static class NegativeData {

        @JsonProperty("error")
        public String error;
        @JsonProperty("request")
        public String request;
        @JsonProperty("method")
        public String method;
    }
}

