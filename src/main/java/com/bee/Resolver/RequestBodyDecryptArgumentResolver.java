package com.bee.Resolver;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bee.annotation.RequestBodyDecrypt;
import com.bee.entity.JSONObjectWrapper;
import com.bee.entity.MapWapper;
import com.bee.entity.User;
import com.bee.util.Util;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName RequestBodyDecryptArgumentResolver
 * @Description TODO
 * @Author ChenLiLin
 * @Date 2018/9/26 16:10
 * @Version 1.0
 **/
public class RequestBodyDecryptArgumentResolver implements HandlerMethodArgumentResolver {
    /**
     * supportsParameter：用于判定是否需要处理该参数分解，
     * 返回true为需要，并会去调用下面的方法resolveArgument()
     * @param parameter
     * @return
     */
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(RequestBodyDecrypt.class) ;
    }
    /**
     * 用于处理参数分解的方法，
     * 返回的Object就是controller方法上的形参对象
     * @Description: TODO
     * @author ChenLiLin
     * @date 2018/9/26 14:36
     * @param
     * @return
     */
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        //获取全部的所有参数
        Map<String, String[]> parMap=request.getParameterMap();
        //获取加密的postBody
        String encryBody = getEncryBody(request);
        System.out.println("获取需要解密的数据body==="+encryBody);
        //解密
        String decryptBody = encryBody;
        if (false) {
            decryptBody = Util.strDecrypt(encryBody);
        }
        System.out.println("body的解密后的数据是-----"+decryptBody);
        Object result;
        Class clazz = parameter.getParameterType();
        //获取类型的
        System.out.println(clazz.getName());

        if (StringUtils.isEmpty(decryptBody)) {
            System.out.println("body is null ");
            return null;
        }
//        if (clazz.equals(List.class)){
//            result= JSON.parseArray(decryptBody);
//        }
//        // 利用fastjson转换为对应的类型
//        if(JSONObjectWrapper.class.isAssignableFrom(parameter.getParameterType())){
//            result= new JSONObjectWrapper(JSON.parseObject(decryptBody));
//        }
        result= JSON.parseObject(decryptBody.toString(), parameter.getParameterType());
        //如果有key则获取key的值返回
        String key=parameter.getParameterAnnotation(RequestBodyDecrypt.class).value();
        if (StringUtils.isNotEmpty(key)){
            JSONObject data= JSON.parseObject(decryptBody.toString());
            //获取json的key
            result=data.get(key);

        }
        //mapWrapper类型
        if (MapWapper.class.isAssignableFrom(clazz)){
            MapWapper mapWrapper=new MapWapper();
            Map target =(Map) JSON.parseObject(decryptBody.toString());
            mapWrapper.setInnerMap(target);
            result=(Object)mapWrapper;
        }
        System.out.println("result:"+result);

//        return new User("测试",1);
        return result;
    }

    /**
     * @Description: 获取方法体的数据
     * @author ChenLiLin
     * @date 2018/9/27 14:36
     * @param
     * @return
     */
    public String getEncryBody(HttpServletRequest request) throws Exception{

        BufferedReader br = request.getReader();
        String str, encryBody = "";
        while ((str = br.readLine()) != null) {
            encryBody += str;
        }
        return encryBody;
    }

    /**
     * @Description: 处理表单数据（解密）目前还没写
     * @author ChenLiLin
     * @date  14:37
     * @param
     * @return
     */
    public Map<String, String[]> decryptMap(Map<String,String[]> parMap){
        Map<String, String[]> map = new HashMap<>();

        for (Map.Entry<String,String[]> entry : parMap.entrySet()){
            System.out.println("key="+entry.getKey()+";"+"vaule="+entry.getValue());
            map.put(entry.getKey(),entry.getValue());
        }
        return map;
    }
}