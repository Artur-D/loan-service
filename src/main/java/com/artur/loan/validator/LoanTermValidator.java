package com.artur.loan.validator;

import com.artur.loan.conf.CommonConfig;
import com.artur.loan.date.DateProvider;
import com.artur.loan.dto.request.LoanRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component(LoanTermValidator.BEAN_NAME)
public class LoanTermValidator implements Validatable<LoanRequestDto> {

    public static final String BEAN_NAME = "loanTermValidator";

    @Autowired
    CommonConfig config;

    @Autowired
    DateProvider dateProvider;

    @Override
    public boolean validate(LoanRequestDto loanRequestDto) {
        int minDays = config.getMinTerm();
        int maxDays = config.getMaxTerm();

        LocalDateTime now = dateProvider.now();
        LocalDate minTerm = now.toLocalDate().plusDays(minDays);
        LocalDate maxTerm = now.toLocalDate().plusDays(maxDays);

        LocalDate termToCheck = loanRequestDto.getTerm();

        // TODO yes, it can be "simplified" but I consider this a bit more readable
        if (minTerm.isAfter(termToCheck) || maxTerm.isBefore(termToCheck)) {
            return false;
        }
        return true;
    }

    // TODO can be configured outside
    @Override
    public String getErrorMessage() {
        return "Term is not valid";
    }

}
