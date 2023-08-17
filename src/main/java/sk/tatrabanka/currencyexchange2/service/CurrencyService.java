package sk.tatrabanka.currencyexchange2.service;

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

    private CurrencyData fetchData(String url) {
        ResponseEntity<CurrencyData> response = restTemplate.getForEntity(url, CurrencyData.class);
        CurrencyData currencyData = Objects.requireNonNull(response.getBody());
        return currencyData;
    }

    @Override
    public List<Currency> getAllCurrency() {
        CurrencyData currencyData = fetchData(apiUrl + "/latest?apikey=" + apiKey);

        for (Map.Entry<String, Double> entry : currencyData.getData().entrySet()) {
            Currency currency = new Currency();
            currency.setCurrencyCode(entry.getKey());
            currency.setExchangeRate(entry.getValue());
            currencyRepository.save(currency);
        }
        return currencyRepository.findAll();
    }



    @Override
    public ExchangeResponse exchangeCurrencies(ExchangeRequest request) {
        try {
            Currency originalCurrency = null;
            Currency targetCurrency = null;

            getAllCurrency();

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
