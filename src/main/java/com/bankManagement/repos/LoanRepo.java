package com.bankManagement.repos;

import com.bankManagement.model.LoansModel;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LoanRepo extends MongoRepository<LoansModel, String> {
}
