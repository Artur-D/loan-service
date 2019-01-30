package com.artur.loan.dto.response;

import com.artur.loan.model.Loan;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class LoanResponseDto implements RestResponse {

    private final Long id;
    private final LocalDate paymentDate;
    private final BigDecimal amount;
    private final BigDecimal totalAmountToPay;
    private final String currency;

    public LoanResponseDto(Loan loan) {
        id = loan.getId();
        paymentDate = loan.getPaymentDate();
        amount = loan.getAmount();
        totalAmountToPay = loan.getTotalAmountToPay();
        currency = loan.getCurrency();
    }
}
