package com.springmongotests.demo.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Hobby {
    private String hobbyName;
    private int perWeek;
}
