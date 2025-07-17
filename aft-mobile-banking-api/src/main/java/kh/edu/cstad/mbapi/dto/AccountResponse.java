package kh.edu.cstad.mbapi.dto;

import java.math.BigDecimal;

public record AccountResponse(
        String actNo,
        String actName,
        String actCurrency,
        BigDecimal balance,
        Boolean isHide,
        String accountType
) {
}
