package com.artur.loan.service;

import com.artur.loan.dto.request.LoanRequestDto;
import com.artur.loan.dto.response.RestResponse;

public interface LoanService {
    RestResponse applyForLoan(LoanRequestDto loanApplication);

    RestResponse extendLoan(Long loanId);
}
