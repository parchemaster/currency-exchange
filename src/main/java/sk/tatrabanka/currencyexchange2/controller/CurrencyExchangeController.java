package sk.tatrabanka.currencyexchange2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sk.tatrabanka.currencyexchange2.data.Currency;
import sk.tatrabanka.currencyexchange2.data.ExchangeRequest;
import sk.tatrabanka.currencyexchange2.data.ExchangeResponse;
import sk.tatrabanka.currencyexchange2.service.CurrencyService;

import java.util.List;

@RestController
@RequestMapping("/currency")
public class CurrencyExchangeController {

    @Autowired
    private CurrencyService currencyService;

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Currency>> getAllCurrenciesData() {
        List<Currency> response = currencyService.getAllCurrency();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(value = "/exchange", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ExchangeResponse> exchange(@RequestBody ExchangeRequest request) {
        ExchangeResponse response = currencyService.exchangeCurrencies(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
