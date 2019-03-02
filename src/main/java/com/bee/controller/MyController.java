package com.bee.controller;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @ClassName MyController
 * @Description TODO
 * @Author ChenLiLin
 * @Date 2018/9/25 10:20
 * @Version 1.0
 **/
@Controller
@RequestMapping(value = "/bee")
public class MyController {
    private Logger log = LoggerFactory.getLogger(MyController.class);
    @RequestMapping(value = "/b",method = RequestMethod.POST)
    public String index(@RequestBody String list){
        System.out.println("s");
        System.out.println("controller:---"+JSON.toJSONString(list));


        return "index.jsp";
    }



    @RequestMapping(value = "/a",method = RequestMethod.GET)
    @ResponseBody
    public String goController(){
        System.out.println("s");
        return "index.jsp";
    }
}