package com.artur.loan.service;

import com.artur.loan.LoanUtils;
import com.artur.loan.conf.CommonConfig;
import com.artur.loan.dao.LoanRepository;
import com.artur.loan.dto.request.LoanRequestDto;
import com.artur.loan.dto.response.LoanResponseDto;
import com.artur.loan.dto.response.RestResponse;
import com.artur.loan.exception.LoanValidationException;
import com.artur.loan.model.Loan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class LoanServiceImpl implements LoanService {

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    ValidatorService validatorService;

    @Autowired
    private CommonConfig commonConfig;

    @Override
    @Transactional
    public RestResponse applyForLoan(LoanRequestDto loanRequestDto) {
        List<String> errors = validatorService.validateFor(ValidatorService.OperationType.APPLY_FOR_LOAN, loanRequestDto);
        if (!errors.isEmpty()) {
            throw new LoanValidationException(errors);
        }

        Loan loan = Loan.builder()
                .amount(loanRequestDto.getAmount())
                .currency(commonConfig.getDefaultCurrency())
                .interestAmount(LoanUtils.getPercentage(loanRequestDto.getAmount(), commonConfig.getDefaultInterestRateInPercent()))
                .paymentDate(loanRequestDto.getTerm())
                .build();

        loanRepository.save(loan);

        return new LoanResponseDto(loan);
    }

    @Override
    @Transactional
    public RestResponse extendLoan(Long loanId) throws EntityNotFoundException {
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(EntityNotFoundException::new);

        loan.setPaymentDate(loan.getPaymentDate().plusDays(commonConfig.getExtendByDays()));
        return new LoanResponseDto(loan);
    }
}
