package com.bee.controller;

import com.alibaba.fastjson.JSON;
import com.bee.annotation.RequestBodyDecrypt;
import com.bee.annotation.RequestParamDecrypt;
import com.bee.annotation.ResponseBodyEncrypt;
import com.bee.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @ClassName MyDataBindingController
 * @Description TODO
 * @Author ChenLiLin
 * @Date 2018/9/26 16:15
 * @Version 1.0
 **/
@Controller
@RequestMapping(value = "/data/binding")
public class MyDataBindingController {

    @RequestMapping(value = "/test")
    @ResponseBodyEncrypt(encrypt = false)
    public User getUser(@RequestBodyDecrypt User list, String name, HttpServletRequest request, HttpSession session){
        System.out.println("controller:name---"+name);
        System.out.println("controller:token---"+request.getHeader("token"));
//        for(int i=0;i<list.size();i++){
//            User u = (User) list.get(i);
//            System.out.println(JSON.toJSONString(u));
//        }
        System.out.println("controller:---"+JSON.toJSONString(list));
        return list;
    }

    @RequestMapping(value = "/form", method = RequestMethod.POST)
    @ResponseBodyEncrypt
    public User getUserForm(@RequestParamDecrypt String name, @RequestParamDecrypt String age){

        System.out.println("name:"+name);
        System.out.println("age:"+age);


        return new User("user",12);
    }


}