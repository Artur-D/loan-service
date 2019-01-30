package com.artur.loan.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class ErrorResponseDto implements RestResponse {

    private final List<String> errorMessages;

}
