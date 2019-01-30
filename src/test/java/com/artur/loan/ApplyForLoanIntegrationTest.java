package com.artur.loan;


import com.artur.loan.conf.CommonConfig;
import com.artur.loan.dto.request.LoanRequestDto;
import com.artur.loan.dto.response.LoanResponseDto;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDate;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApplyForLoanIntegrationTest {

    @Autowired
    private CommonConfig commonConfig;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void applyForLoan() {
        BigDecimal amount = BigDecimal.valueOf(99000);
        LocalDate term = LocalDate.now().plusDays(5);

        LoanRequestDto loanRequestDto = new LoanRequestDto(amount, term);
        BigDecimal expectedTotalAmount = amount.add(LoanUtils.getPercentage(amount, commonConfig.getDefaultInterestRateInPercent()));

        ResponseEntity<LoanResponseDto> responseEntity = restTemplate.postForEntity("/loan/apply", loanRequestDto, LoanResponseDto.class);
        LoanResponseDto loanApplicationResponse = responseEntity.getBody();

        Assert.assertEquals("Wrong status code", HttpStatus.CREATED, responseEntity.getStatusCode());
        Assert.assertEquals("Wrong total amount to pay", expectedTotalAmount, loanApplicationResponse.getTotalAmountToPay());
        Assert.assertEquals("Wrong term", term, loanApplicationResponse.getPaymentDate());
    }


}
