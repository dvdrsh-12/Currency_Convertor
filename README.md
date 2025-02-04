# Currency_Convertor

# Currency Converter API Documentation

## Overview
This API provides real-time currency conversion and exchange rate information using the Open Exchange Rates API.

## Base URL
```
http://localhost:8080/api
```

## Endpoints

### 1. Get Exchange Rates
Returns current exchange rates for a specified base currency.

**Endpoint:** `GET /rates`

**Query Parameters:**
- `base` (optional): Base currency code (e.g., USD, EUR)
  - Default: USD

**Response Format:**
```json
{
    "base": "USD",
    "rates": {
        "EUR": 0.85,
        "GBP": 0.73,
        "JPY": 110.25
        // ... other currencies
    }
}
```

**Status Codes:**
- 200: Successful response
- 400: Invalid currency code
- 503: External API unavailable

### 2. Convert Currency
Converts an amount from one currency to another using current exchange rates.

**Endpoint:** `POST /convert`

**Request Format:**
```json
{
    "from": "USD",
    "to": "EUR",
    "amount": 100.00
}
```

**Request Fields:**
- `from`: Source currency code (required)
- `to`: Target currency code (required)
- `amount`: Amount to convert (required, must be positive)

**Response Format:**
```json
{
    "from": "USD",
    "to": "EUR",
    "amount": 100.00,
    "convertedAmount": 85.00
}
```

**Status Codes:**
- 200: Successful conversion
- 400: Invalid request (invalid currency codes or amount)
- 503: External API unavailable

## Error Responses
All error responses follow this format:
```json
{
    "status": "error type",
    "message": "detailed error message"
}
```

## Common Error Cases
1. Invalid Currency Code
```json
{
    "status": "Bad Request",
    "message": "Invalid currency code"
}
```

2. External API Unavailable
```json
{
    "status": "Server Error",
    "message": "An unexpected error occurred"
}
```
