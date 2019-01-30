package com.artur.loan.controller;

import com.artur.loan.dto.request.LoanRequestDto;
import com.artur.loan.dto.response.ErrorResponseDto;
import com.artur.loan.dto.response.RestResponse;
import com.artur.loan.exception.LoanValidationException;
import com.artur.loan.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityNotFoundException;

@RestController
@RequestMapping(value = "/loan")
public class LoanController {

    @Autowired
    LoanService loanService;

    @RequestMapping("/")
    public String index() {
        return "Congratulations from LoanController.java";
    }

    @PostMapping(path = "/apply",
            consumes = "application/json",
            produces = "application/json")
    public ResponseEntity<RestResponse> applyForLoan(@RequestBody LoanRequestDto loanRequestDto) {
        RestResponse body = loanService.applyForLoan(loanRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(body);

        // TODO add GET endpoint and return ResponseEntity.created(URI uri)
    }

    @PatchMapping(path = "/extend/{id}",
            consumes = "application/json",
            produces = "application/json")
    public ResponseEntity<RestResponse> extendLoan(@PathVariable("id") Long loanId) {
        RestResponse response = loanService.extendLoan(loanId);
        return ResponseEntity.ok(response);

        // TODO add validator(s) or set in config how many times it can be done
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ResponseBody
    public ResponseEntity handleApplicationRejected() {
        return ResponseEntity.notFound().build();

        // TODO add GET endpoint and return ResponseEntity.notFound().location(URI uri)
    }

    @ExceptionHandler(LoanValidationException.class)
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    @ResponseBody
    public ResponseEntity<RestResponse> handleApplicationRejected(LoanValidationException e) {
        RestResponse response = new ErrorResponseDto(e.getErrorMessages());
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }
}