package com.example.service;

import com.example.dto.ConversionRequest;
import com.example.dto.ConversionResponse;
import com.example.dto.ExchangeRatesResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class CurrencyService {
    private static final String API_URL = "https://open.exchangerate-api.com/v6/latest/";
    private final RestTemplate restTemplate;

    public CurrencyService() {
        this.restTemplate = new RestTemplate();
    }

    public ExchangeRatesResponse getExchangeRates(String base) {
        String url = API_URL + base;
        Map<String, Object> response = restTemplate.getForObject(url, Map.class);
        
        ExchangeRatesResponse ratesResponse = new ExchangeRatesResponse();
        ratesResponse.setBase(base);
        ratesResponse.setRates(convertRates(response.get("rates")));
        
        return ratesResponse;
    }

    public ConversionResponse convertCurrency(ConversionRequest request) {
        ExchangeRatesResponse rates = getExchangeRates(request.getFrom());
        
        Double rate = rates.getRates().get(request.getTo());
        if (rate == null) {
            throw new IllegalArgumentException("Invalid currency code");
        }
        
        double convertedAmount = request.getAmount() * rate;
        
        return new ConversionResponse(
            request.getFrom(), 
            request.getTo(), 
            request.getAmount(), 
            convertedAmount
        );
    }

    private Map<String, Double> convertRates(Object ratesObj) {
        return ((Map<String, Number>) ratesObj).entrySet().stream()
            .collect(java.util.stream.Collectors.toMap(
                Map.Entry::getKey, 
                entry -> entry.getValue().doubleValue()
            ));
    }
}