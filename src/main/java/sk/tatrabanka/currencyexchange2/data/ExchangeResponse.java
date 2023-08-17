package sk.tatrabanka.currencyexchange2.data;

import lombok.Data;

@Data
public class ExchangeResponse {
    private String targetCode;
    private Double price;
}
