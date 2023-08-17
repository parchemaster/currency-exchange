package sk.tatrabanka.currencyexchange2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sk.tatrabanka.currencyexchange2.data.Currency;


@Repository
public interface CurrencyRepository extends JpaRepository<Currency, Long> {
    Currency findCurrencyByCurrencyCode(String currencyCode);

}
