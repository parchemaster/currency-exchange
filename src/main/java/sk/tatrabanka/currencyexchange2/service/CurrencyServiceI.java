package sk.tatrabanka.currencyexchange2.service;


import sk.tatrabanka.currencyexchange2.data.Currency;
import sk.tatrabanka.currencyexchange2.data.ExchangeRequest;
import sk.tatrabanka.currencyexchange2.data.ExchangeResponse;

import java.util.List;

public interface CurrencyServiceI {
    List<Currency> getAllCurrency();
    ExchangeResponse exchangeCurrencies(ExchangeRequest request);
}
