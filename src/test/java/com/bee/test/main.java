package com.bee.test;

import com.alibaba.fastjson.JSON;
import com.bee.entity.User;
import com.bee.util.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName main
 * @Description TODO
 * @Author ChenLiLin
 * @Date 2018/9/27 14:27
 * @Version 1.0
 **/
public class main {
    public static void main(String[] args) {
        User u = new User("asd",12);
        List<User> list = new ArrayList<>();
        list.add(new User("asd",12));
        list.add(new User("asd",12));
        list.add(new User("asd",12));
        list.add(new User("asd",12));
        list.add(new User("asd",12));
        list.add(new User("asd",12));
        list.add(new User("asd",12));


        System.out.println(JSON.toJSONString(list));

        System.out.println(Util.strEncrypt("1234567"));

    }
}