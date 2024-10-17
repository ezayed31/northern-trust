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

//    public String getEnrichedRate(String baseCurrency, String targetCurrency) {
//        String url = apiUrl + apiKey + "/enriched/" + baseCurrency + "/" + targetCurrency;
//
//        try {
//            return restTemplate.getForObject(url, String.class);
//        } catch (HttpClientErrorException e) {
//            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
//                return "{\"error\": \"Currency pair not found or invalid.\"}";
//            } else {
//                return "{\"error\": \"An error occurred while fetching the enriched data.\"}";
//            }
//        }
//    }
//
//    public String getHistoricalRates(String baseCurrency, int year, int month, int day) {
//        String url = apiUrl + apiKey + "/history/" + baseCurrency + "/" + year + "/" + month + "/" + day;
//
//        try {
//            return restTemplate.getForObject(url, String.class);
//        } catch (HttpClientErrorException e) {
//            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
//                return "{\"error\": \"Historical data not found or invalid date.\"}";
//            } else {
//                return "{\"error\": \"An error occurred while fetching historical data.\"}";
//            }
//        }
//    }
//
//    public String getHistoricalRatesWithAmount(String baseCurrency, int year, int month, int day, double amount) {
//        String url = apiUrl + apiKey + "/history/" + baseCurrency + "/" + year + "/" + month + "/" + day + "/" + amount;
//
//        try {
//            return restTemplate.getForObject(url, String.class);
//        } catch (HttpClientErrorException e) {
//            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
//                return "{\"error\": \"Historical data or amount not found or invalid.\"}";
//            } else {
//                return "{\"error\": \"An error occurred while fetching historical data and conversion.\"}";
//            }
//        }
//    }
}
