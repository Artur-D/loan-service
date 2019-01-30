package com.artur.loan.validator;

import com.artur.loan.conf.CommonConfig;
import com.artur.loan.dto.request.LoanRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

// TODO we could configure this rule per currency
@Component(LoanAmountValidator.BEAN_NAME)
public class LoanAmountValidator implements Validatable<LoanRequestDto> {

    public static final String BEAN_NAME = "loanAmountValidator";

    @Autowired
    CommonConfig config;

    @Override
    public boolean validate(LoanRequestDto loanRequestDto) {
        BigDecimal minAmount = config.getMinAmount();
        BigDecimal maxAmount = config.getMaxAmount();
        BigDecimal amount = loanRequestDto.getAmount();

        return amount.compareTo(minAmount) >= 0 && amount.compareTo(maxAmount) <= 0;
    }

    // TODO can be configured outside + based on currency
    @Override
    public String getErrorMessage() {
        return "Loan amount has to be in range from " + config.getMinAmount() + " to " + config.getMaxAmount();
    }
}
