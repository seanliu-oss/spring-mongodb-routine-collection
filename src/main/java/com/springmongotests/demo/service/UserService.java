package com.springmongotests.demo.service;

import com.mongodb.client.result.UpdateResult;
import com.springmongotests.demo.data.User;
import com.springmongotests.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.data.mongodb.SessionSynchronization;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final MongoTemplate mongoTemplate;

    private final UserRepository userRepository;

    private final MongoTransactionManager mongoTransactionManager;


    public List<User> findUserByHobby(String hobbyName){
        return userRepository.findByHobbiesHobbyName(hobbyName);
    }

    public Optional<User> findUserById(String Id){
        return userRepository.findById(Id);
    }

    public UpdateResult removeHobbyEntry(String id,String hobbyName){
        Query removeQuery = Query.query(Criteria.where("id").is(id));
        Update removeUpdate = new Update().pull("hobbies", Query.query(Criteria.where("hobbyName").is(hobbyName)));
        return mongoTemplate.updateMulti(removeQuery, removeUpdate, User.class);
    }

    public UpdateResult updateSpecificHobbyEntry(String hobbyName, int toPerWeek) {
        Query hobbyQuery = Query.query(Criteria.where("hobbies.hobbyName").is(hobbyName));
        Update removeUpdate = new Update().set("hobbies.$[entry].perWeek",toPerWeek).filterArray(Criteria.where("entry.hobbyName").is(hobbyName));
        return mongoTemplate.updateMulti(hobbyQuery, removeUpdate, User.class);
    }

    public UpdateResult updateAllHobbyEntries( int toPerWeek) {
        Query hobbyQuery = new Query();
        Update removeUpdate = new Update().set("hobbies.$[].perWeek",toPerWeek);
        return mongoTemplate.updateMulti(hobbyQuery, removeUpdate, User.class);
    }

    public UpdateResult removeLuckyNumber(String id, int i) {
        Query removeQuery = Query.query(Criteria.where("id").is(id));
        Update removeUpdate = new Update().pull("luckyNumbers", i);
        return mongoTemplate.updateMulti(removeQuery, removeUpdate, User.class);
    }

    public UpdateResult removeLuckyNumbers(String id, Integer[] numbers) {
        Query removeQuery = Query.query(Criteria.where("id").is(id));
        Update removeUpdate = new Update().pullAll("luckyNumbers", numbers);
        return mongoTemplate.updateMulti(removeQuery, removeUpdate, User.class);
    }

    public void transactionalAddUsers(List<User> userList){
        mongoTemplate.setSessionSynchronization(SessionSynchronization.ALWAYS);

        TransactionTemplate transactionTemplate = new TransactionTemplate(mongoTransactionManager);
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                mongoTemplate.insertAll(userList);
            }
        });
    }

    @Transactional
    public void annotatedAddUsers(List<User> userList){
        userRepository.saveAll(userList);
    }
}
