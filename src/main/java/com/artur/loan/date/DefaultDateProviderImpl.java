package com.artur.loan.date;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Component
public class DefaultDateProviderImpl implements DateProvider {

    @Override
    public LocalDateTime now() {
        return LocalDateTime.now();
    }
}
