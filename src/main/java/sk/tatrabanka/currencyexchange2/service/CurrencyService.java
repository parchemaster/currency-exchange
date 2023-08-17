package sk.tatrabanka.currencyexchange2.service;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import sk.tatrabanka.currencyexchange2.data.Currency;
import sk.tatrabanka.currencyexchange2.data.CurrencyData;
import sk.tatrabanka.currencyexchange2.data.ExchangeRequest;
import sk.tatrabanka.currencyexchange2.data.ExchangeResponse;
import sk.tatrabanka.currencyexchange2.exception.NotFoundException;
import sk.tatrabanka.currencyexchange2.repository.CurrencyRepository;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Service
public class CurrencyService implements CurrencyServiceI{

    @Autowired
    private CurrencyRepository currencyRepository;

    @Value("${api.key}")
    private String apiKey;

    @Value("${api.url}")
    private String apiUrl;

    @Autowired
    private RestTemplate restTemplate;

    private List<Currency> fetchData(String url) {
        ResponseEntity<CurrencyData> response = restTemplate.getForEntity(url, CurrencyData.class);
        CurrencyData currencyData = Objects.requireNonNull(response.getBody());

        List<Currency> currencies = new ArrayList<>();
        for (Map.Entry<String, Double> entry : currencyData.getData().entrySet()) {
            Currency currency = new Currency();
            currency.setCurrencyCode(entry.getKey());
            currency.setExchangeRate(entry.getValue());
            currencies.add(currency);
        }
        return currencies;
    }

    @Override
    public List<Currency> getAllCurrency() {
        return currencyRepository.findAll();
    }

    @PostConstruct
    public void updateCurrencies() {
        List<Currency> currencies = fetchData(apiUrl + "/latest?apikey=" + apiKey);
        currencyRepository.saveAll(currencies);
    }

    @Override
    public ExchangeResponse exchangeCurrencies(ExchangeRequest request) {
        try {
            Currency originalCurrency = null;
            Currency targetCurrency = null;

            if (StringUtils.hasText(request.getOriginalCode())) {
                originalCurrency = currencyRepository.findCurrencyByCurrencyCode(request.getOriginalCode());
            }

            if (StringUtils.hasText(request.getTargetCode())) {
                targetCurrency = currencyRepository.findCurrencyByCurrencyCode(request.getTargetCode());
            }


            if (originalCurrency != null && targetCurrency != null) {
                ExchangeResponse response = new ExchangeResponse();
                final Double convertedMoney = convertMoney(request.getMoney(), originalCurrency.getExchangeRate(), targetCurrency.getExchangeRate());
                response.setPrice(convertedMoney);
                response.setTargetCode(targetCurrency.getCurrencyCode());
                return response;
            } else {
                throw new NotFoundException("We didn't find currencies. Please try again.");
            }

        } catch (Exception e) {
            throw new NotFoundException(e.getMessage());
        }
    }

    private Double convertMoney(Double money, Double originalToUSD, Double targetToUSD) {
        Double dollars = money / originalToUSD;
        Double targetMoney = dollars * targetToUSD;
        return targetMoney;
    }
}
