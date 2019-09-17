package com.hewentian.entity;

import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * <b>User</b> 是 测试protostuff序列化和反序列化使用
 * </p>
 *
 * @author <a href="mailto:wentian.he@qq.com">hewentian</a>
 * @date 2019-03-11 21:33:58
 * @since JDK 1.8
 */
public class User {
    private Integer id;
    private int age;
    private String name;
    private List<String> titles;
    private Location location;
    private int[] number;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getTitles() {
        return titles;
    }

    public void setTitles(List<String> titles) {
        this.titles = titles;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public int[] getNumber() {
        return number;
    }

    public void setNumber(int[] number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", age=" + age +
                ", name='" + name + '\'' +
                ", titles=" + titles +
                ", location=" + location +
                ", number=" + Arrays.toString(number) +
                '}';
    }
}
