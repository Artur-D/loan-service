package com.artur.loan.validator

import com.artur.loan.conf.CommonConfig
import com.artur.loan.date.DateProvider
import com.artur.loan.dto.request.LoanRequestDto
import spock.lang.Specification
import spock.lang.Unroll

import java.time.LocalDate
import java.time.LocalDateTime

class LoanNightValidatorSpec extends Specification {

    @Unroll
    def "should check isValid"() {
        given:
        DateProvider mockDateProvider = Mock(DateProvider)
        mockDateProvider.now() >> _now

        CommonConfig mockConfig = new CommonConfig(maxAmount: 999)

        and:
        LoanNightValidator validator = new LoanNightValidator(dateProvider: mockDateProvider, config: mockConfig)

        LoanRequestDto testRequest = new LoanRequestDto(_amount as BigDecimal, LocalDate.of(2012, 12, 12))

        expect:
        validator.validate(testRequest) == _valid

        where:
        _amount | _now                                       | _valid
        // amount not max
        1000    | LocalDateTime.of(2012, 12, 10, 0, 1, 1)    | true
        998     | LocalDateTime.of(2012, 12, 10, 0, 1, 1)    | true
        // amount max but time outside range
        999     | LocalDateTime.of(2012, 12, 16, 6, 0, 1)    | true
        999     | LocalDateTime.of(2012, 12, 12, 23, 59, 59) | true

        999     | LocalDateTime.of(2012, 12, 10, 0, 0, 0)    | false
        999     | LocalDateTime.of(2012, 12, 10, 0, 0, 1)    | false
        999     | LocalDateTime.of(2012, 12, 15, 5, 59, 59)  | false
        999     | LocalDateTime.of(2012, 12, 16, 6, 0, 0)    | false
    }

}
