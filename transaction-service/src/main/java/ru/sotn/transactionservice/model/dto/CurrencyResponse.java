package ru.sotn.transactionservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Set;

@Data

public class CurrencyResponse {
    private Info info;
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Info {
        private String timestamp;
        private String quote;
    }
}
