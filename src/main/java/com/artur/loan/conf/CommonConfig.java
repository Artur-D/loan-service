package com.artur.loan.conf;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

@Configuration
@ConfigurationProperties("loan")
@Getter
@Setter
public class CommonConfig {

    // TODO this amount could be per currency
    private BigDecimal minAmount;
    private BigDecimal maxAmount;

    private int minTerm;
    private int maxTerm;

    private String defaultCurrency;
    private int defaultInterestRateInPercent;
    private int extendByDays;


}
