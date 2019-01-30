package com.artur.loan.validator;

import com.artur.loan.conf.CommonConfig;
import com.artur.loan.date.DateProvider;
import com.artur.loan.dto.request.LoanRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalTime;

@Component(LoanNightValidator.BEAN_NAME)
public class LoanNightValidator implements Validatable<LoanRequestDto> {

    public static final String BEAN_NAME = "loanNightValidator";

    @Autowired
    CommonConfig config;

    @Autowired
    DateProvider dateProvider;

    private static final LocalTime RULE_TIME_LOWER_LIMIT = LocalTime.MIDNIGHT;
    private static final LocalTime RULE_TIME_UPPER_LIMIT = LocalTime.of(6, 0, 0);

    @Override
    public boolean validate(LoanRequestDto loanRequestDto) {
        BigDecimal maxAmount = config.getMaxAmount();
        LocalTime nowInLocalTime = dateProvider.now().toLocalTime();

        BigDecimal amount = loanRequestDto.getAmount();


        // TODO yes, it can be "simplified" but I consider this a bit more readable
        if (amount.compareTo(maxAmount) == 0
                && (
                (nowInLocalTime.isAfter(RULE_TIME_LOWER_LIMIT) || nowInLocalTime.equals(RULE_TIME_LOWER_LIMIT))
                        && (nowInLocalTime.isBefore(RULE_TIME_UPPER_LIMIT) || nowInLocalTime.equals(RULE_TIME_UPPER_LIMIT))
        )) {
            return false;
        }
        return true;
    }

    // TODO can be configured outside
    @Override
    public String getErrorMessage() {
        return "During these hours lona with maximum amount cannot be issued";
    }

}
