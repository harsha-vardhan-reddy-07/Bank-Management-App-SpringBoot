package com.bankManagement.repos;

import com.bankManagement.model.DepositModel;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DepositRepo extends MongoRepository<DepositModel, String> {
}
