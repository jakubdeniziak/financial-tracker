package com.jakubdeniziak.financialtracker.util;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class CurrencyFormatter {

    public String format(BigDecimal value, String currency) {
        switch (currency) {
            case "USD" -> {
                return String.format("$ %s", value);
            }
            case "EUR" -> {
                return String.format("€ %s", value);
            }
            case "GBP" -> {
                return String.format("£ %s", value);
            }
            default -> {
                return String.format("%s %s", value, currency);
            }
        }
    }

}
