package com.artur.loan.dao;

import com.artur.loan.model.Loan;
import org.springframework.data.repository.CrudRepository;

public interface LoanRepository extends CrudRepository<Loan, Long> {

}
