package com.hewentian.jackson.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.ToString;

/**
 * 没有getter、setter方法
 */
@ToString
public class User3 {
    private Integer id;

    private String name;

    private Integer age;

    private String gender;

    private String phone;

    private String address;

    @JsonCreator
    public User3(String name, Integer age, String gender) {
        this.name = name;
        this.age = age;
        this.gender = gender;
    }
}
