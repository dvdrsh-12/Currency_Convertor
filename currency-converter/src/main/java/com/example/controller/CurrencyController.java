package com.example.controller;

import com.example.dto.ConversionRequest;
import com.example.dto.ConversionResponse;
import com.example.dto.ExchangeRatesResponse;
import com.example.service.CurrencyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CurrencyController {
    private final CurrencyService currencyService;

    public CurrencyController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @GetMapping("/rates")
    public ExchangeRatesResponse getExchangeRates(
        @RequestParam(defaultValue = "USD") String base
    ) {
        return currencyService.getExchangeRates(base);
    }

    @PostMapping("/convert")
    public ResponseEntity<ConversionResponse> convertCurrency(
        @RequestBody ConversionRequest request
    ) {
        ConversionResponse response = new ConversionResponse();
        response.setFrom(request.getFrom());
        response.setTo(request.getTo());
        response.setAmount(request.getAmount());
        
        ExchangeRatesResponse rates = currencyService.getExchangeRates(request.getFrom());
        Double rate = rates.getRates().get(request.getTo());
        
        if (rate == null) {
            throw new IllegalArgumentException("Invalid currency code");
        }
        
        response.setConvertedAmount(request.getAmount() * rate);
        return ResponseEntity.ok(response);
    }
}