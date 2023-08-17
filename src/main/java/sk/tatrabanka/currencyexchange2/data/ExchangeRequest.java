package sk.tatrabanka.currencyexchange2.data;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ExchangeRequest {
    private String originalCode;
    private String targetCode;
    private String originalName;
    private String targetName;
    private Double money;
}
