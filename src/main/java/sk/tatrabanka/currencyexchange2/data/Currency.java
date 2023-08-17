package sk.tatrabanka.currencyexchange2.data;

import jakarta.persistence.*;
import lombok.Data;



@Entity
@Data
public class Currency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "currency_code")
    private String currencyCode;

    @Column(name = "exchange_rate")
    private Double exchangeRate;

}