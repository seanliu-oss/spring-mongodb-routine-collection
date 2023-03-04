package com.springmongotests.demo.repository;

import com.springmongotests.demo.data.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserRepository extends MongoRepository<User,String> {
    List<User> findByAgeBetween(int min, int max);
    List<User> findByHobbiesHobbyName(String hobbyName);
}
