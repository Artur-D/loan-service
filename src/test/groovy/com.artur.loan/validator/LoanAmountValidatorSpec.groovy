package com.artur.loan.validator

import com.artur.loan.conf.CommonConfig
import com.artur.loan.dto.request.LoanRequestDto
import spock.lang.Specification
import spock.lang.Unroll

import java.time.LocalDate

class LoanAmountValidatorSpec extends Specification {

    @Unroll
    def "check loan min and max amount"() {
        given:
        CommonConfig mockConfig = new CommonConfig(minAmount: 999.5, maxAmount: 99998.99)

        and:
        LoanAmountValidator validator = new LoanAmountValidator(config: mockConfig)

        LoanRequestDto testRequest = new LoanRequestDto(_amount as BigDecimal, LocalDate.of(2012, 1, 8))

        expect:
        validator.validate(testRequest) == _valid

        where:
        _amount  | _valid
        // min
        999      | false
        999.49   | false
        999.50   | true
        999.51   | true
        1000     | true
        // max
        99997    | true
        99998.98 | true
        99998.99 | true
        99999    | false

    }
}
