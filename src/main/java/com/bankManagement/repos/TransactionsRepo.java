package com.bankManagement.repos;

import com.bankManagement.model.TransactionsModel;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TransactionsRepo extends MongoRepository<TransactionsModel, String> {
}
