package sk.tatrabanka.currencyexchange2.data;

import lombok.Data;

import java.util.Map;

@Data
public class CurrencyData {
    private Map<String, Double> data;
}