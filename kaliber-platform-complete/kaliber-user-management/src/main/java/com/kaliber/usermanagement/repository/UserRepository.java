package com.kaliber.usermanagement.repository;

import com.kaliber.usermanagement.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, String>{

	User findByUsername(String username);

	User findByUserId(String userId);

}