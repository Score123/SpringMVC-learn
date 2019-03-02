package com.bee.entity;

/**
 * @ClassName User
 * @Description TODO
 * @Author ChenLiLin
 * @Date 2018/9/26 16:13
 * @Version 1.0
 **/
public class User {
    String name;
    int age;
    public User(){

    }
    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}