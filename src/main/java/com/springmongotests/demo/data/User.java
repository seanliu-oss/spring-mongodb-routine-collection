package com.springmongotests.demo.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
@NoArgsConstructor
@AllArgsConstructor
@Data
public class User {
    @Id
    String id;
    Integer age;
    List<Hobby> hobbies;
    List<Integer> luckyNumbers;
}
