package com.hewentian.jackson.entity;

import lombok.ToString;

/**
 * 没有getter、setter方法
 */
@ToString
public class User2 {
    private Integer id;

    private String name;

    private Integer age;

    private String gender;

    private String phone;

    private String address;

    /**
     * 注意是 private 的
     */
    private User2() {
    }

    public User2(String name, Integer age, String gender) {
        this.name = name;
        this.age = age;
        this.gender = gender;
    }
}
