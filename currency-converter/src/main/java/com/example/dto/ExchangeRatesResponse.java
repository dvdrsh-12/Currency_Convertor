package com.example.dto;

import lombok.Data;



@Data
public class ExchangeRatesResponse {
    private String base;
    private java.util.Map<String, Double> rates;
}