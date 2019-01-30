package com.artur.loan.validator

import com.artur.loan.conf.CommonConfig
import com.artur.loan.date.DateProvider
import com.artur.loan.dto.request.LoanRequestDto
import spock.lang.Specification
import spock.lang.Unroll

import java.time.LocalDate
import java.time.LocalDateTime

class LoanTermValidatorSpec extends Specification {

    @Unroll
    def "check loan min and max term"() {
        given:
        DateProvider mockDateProvider = Mock(DateProvider)
        LocalDateTime now = LocalDateTime.of(2012, 1, 7, 19, 1, 1)
        mockDateProvider.now() >> now

        CommonConfig mockConfig = new CommonConfig(minTerm: 2, maxTerm: 7)

        and:
        LoanTermValidator validator = new LoanTermValidator(dateProvider: mockDateProvider, config: mockConfig)

        LoanRequestDto testRequest = new LoanRequestDto(1000, _term)

        expect:
        validator.validate(testRequest) == _valid

        where:
        _term                     | _valid
        // min
        LocalDate.of(2012, 1, 8)  | false
        LocalDate.of(2012, 1, 9)  | true
        LocalDate.of(2012, 1, 10) | true
        // max
        LocalDate.of(2012, 1, 13) | true
        LocalDate.of(2012, 1, 14) | true
        LocalDate.of(2012, 1, 15) | false
    }

}
