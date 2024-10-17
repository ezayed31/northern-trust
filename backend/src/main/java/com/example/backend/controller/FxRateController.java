package com.example.backend.controller;

import com.example.backend.service.FxRateService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/fxrates")
public class FxRateController {

    private final FxRateService fxRateService;

    public FxRateController(FxRateService fxRateService) {
        this.fxRateService = fxRateService;
    }

    @GetMapping("/live")
    public String getLiveRates(@RequestParam String baseCurrency) {
        return fxRateService.getLiveRates(baseCurrency);
    }

    @GetMapping("/pair")
    public String getPairRate(@RequestParam String baseCurrency,
                              @RequestParam String targetCurrency,
                              @RequestParam(required = false) Double amount) {
        if (amount != null) {
            return fxRateService.getPairRateWithAmount(baseCurrency, targetCurrency, amount);
        } else {
            return fxRateService.getPairRate(baseCurrency, targetCurrency);
        }
    }

//    @GetMapping("/enriched")
//    public String getEnrichedRate(@RequestParam String baseCurrency,
//                                  @RequestParam String targetCurrency) {
//        return fxRateService.getEnrichedRate(baseCurrency, targetCurrency);
//    }
//
//    @GetMapping("/history")
//    public String getHistoricalRates(@RequestParam String baseCurrency,
//                                     @RequestParam int year,
//                                     @RequestParam int month,
//                                     @RequestParam int day,
//                                     @RequestParam(required = false) Double amount) {
//        if (amount != null) {
//            return fxRateService.getHistoricalRatesWithAmount(baseCurrency, year, month, day, amount);
//        } else {
//            return fxRateService.getHistoricalRates(baseCurrency, year, month, day);
//        }
//    }
}
