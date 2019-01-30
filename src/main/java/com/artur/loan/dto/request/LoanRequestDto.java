package com.artur.loan.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@Getter
public class LoanRequestDto {
    private final BigDecimal amount;
    private final LocalDate term;
}
