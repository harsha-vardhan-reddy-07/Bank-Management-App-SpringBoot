package com.bankManagement.repos;

import com.bankManagement.model.UserModel;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepo extends MongoRepository<UserModel, String> {
    UserModel findByEmail(String email);
}
