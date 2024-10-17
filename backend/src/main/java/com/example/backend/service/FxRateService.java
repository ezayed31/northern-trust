package com.example.backend.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class FxRateService {

    @Value("${exchangerate.api.key}")
    private String apiKey;

    @Value("${exchangerate.api.url}")
    private String apiUrl;

    @Value("${alphavantage.api.key}")
    private String api2Key;

    @Value("${alphavantage.api.url}")
    private String api2Url;

    private final RestTemplate restTemplate;

    public FxRateService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getLiveRates(String baseCurrency) {
        String url = apiUrl + apiKey + "/latest/" + baseCurrency;
        return restTemplate.getForObject(url, String.class);
    }

    public String getPairRate(String baseCurrency, String targetCurrency) {
        String url = apiUrl + apiKey + "/pair/" + baseCurrency + "/" + targetCurrency;

        try {
            return restTemplate.getForObject(url, String.class);
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                return "{\"error\": \"Currency pair not found or invalid.\"}";
            } else {
                return "{\"error\": \"An error occurred while fetching the exchange rate.\"}";
            }
        }
    }

    public String getPairRateWithAmount(String baseCurrency, String targetCurrency, double amount) {
        String url = apiUrl + apiKey + "/pair/" + baseCurrency + "/" + targetCurrency + "/" + amount;

        try {
            return restTemplate.getForObject(url, String.class);
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                return "{\"error\": \"Currency pair or amount not found or invalid.\"}";
            } else {
                return "{\"error\": \"An error occurred while fetching the exchange rate and conversion.\"}";
            }
        }
    }

    public String getEnrichedRate(String baseCurrency, String targetCurrency) {
        String url = apiUrl + apiKey + "/enriched/" + baseCurrency + "/" + targetCurrency;

        try {
            return restTemplate.getForObject(url, String.class);
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                return "{\"error\": \"Currency pair not found or invalid.\"}";
            } else {
                return "{\"error\": \"An error occurred while fetching the enriched data.\"}";
            }
        }
    }

    public String getFxRates(String function, String fromSymbol, String toSymbol, String outputSize, String dataType) {
        String url = api2Url + "/query?function=" + function
                + "&from_symbol=" + fromSymbol
                + "&to_symbol=" + toSymbol
                + "&outputsize=" + outputSize
                + "&datatype=" + dataType
                + "&apikey=" + api2Key;

        try {
            return restTemplate.getForObject(url, String.class);
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                return "{\"error\": \"FX data not found for the specified currency pair.\"}";
            } else {
                return "{\"error\": \"An error occurred while fetching the FX data.\"}";
            }
        }
    }
}
