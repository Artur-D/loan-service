package com.artur.loan.service;

import com.artur.loan.dto.request.LoanRequestDto;
import com.artur.loan.validator.LoanAmountValidator;
import com.artur.loan.validator.LoanNightValidator;
import com.artur.loan.validator.LoanTermValidator;
import com.artur.loan.validator.Validatable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ValidatorService {

    private static final List<String> APPLY_FOR_LOAN_VALIDATORS_NAMES = Arrays.asList(
            LoanAmountValidator.BEAN_NAME,
            LoanNightValidator.BEAN_NAME,
            LoanTermValidator.BEAN_NAME
    );

    private Map<OperationType, List<Validatable>> operationTypeValidatorsMap;

    @Autowired
    private ApplicationContext applicationContext;

    @PostConstruct
    private void initializeValidatorsMap() {
        List<Validatable> applyForLoanValidators = APPLY_FOR_LOAN_VALIDATORS_NAMES.stream()
                .map(beanName -> (Validatable) applicationContext.getBean(beanName))
                .collect(Collectors.toList());

        operationTypeValidatorsMap = Collections.unmodifiableMap(
                Stream.of(
                        new AbstractMap.SimpleEntry<>(OperationType.APPLY_FOR_LOAN, Collections.unmodifiableList(applyForLoanValidators))
                )
                        .collect(Collectors.toMap((entry) -> entry.getKey(), (entry) -> entry.getValue()))
        );
    }


    public List<Validatable> getValidatorsFor(OperationType operationType) {
        return Optional.ofNullable(operationTypeValidatorsMap.get(operationType))
                .orElse(Collections.emptyList());
    }

    public List<String> validateFor(OperationType operationType, LoanRequestDto loanRequestDto) {
        List<String> errorMessages = new ArrayList<>();

        for (Validatable validator : getValidatorsFor(operationType)) {
            if (!validator.validate(loanRequestDto)) {
                errorMessages.add(validator.getErrorMessage());
            }
        }

        return errorMessages;
    }

    enum OperationType {
        APPLY_FOR_LOAN
    }
}
