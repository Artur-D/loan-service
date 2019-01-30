package com.artur.loan.validator;

public interface Validatable<T> {
    boolean validate(T obj);

    String getErrorMessage();
}
