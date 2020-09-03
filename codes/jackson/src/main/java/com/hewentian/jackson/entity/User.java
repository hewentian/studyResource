package com.hewentian.jackson.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * 有getter、setter方法和默认构造方法
 */
@NoArgsConstructor
@ToString
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User {
    private Integer id;

    private String name;

    private Integer age;

    private String gender;

    private String phone;

    @JsonIgnore
    private String address;

    private Date birthday;

    // 自定义属性名
    @JsonProperty("create_time")
    // 用于日期格式化
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
}
