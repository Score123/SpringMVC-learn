package com.bee.Handler;

import com.alibaba.fastjson.JSON;
import com.bee.annotation.ResponseBodyEncrypt;
import com.bee.util.Util;
import org.springframework.core.MethodParameter;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * @ClassName ResponseBodyEncryptReturnValueHandler
 * @Description TODO
 * @Author ChenLiLin
 * @Date 2018/9/27 9:34
 * @Version 1.0
 **/
public class ResponseBodyEncryptReturnValueHandler implements HandlerMethodReturnValueHandler  {

    /**
     * supportsReturnType：用于判定是否指定的注解
     * @param
     * @return
     */
    @Override
    public boolean supportsReturnType(MethodParameter methodParameter) {
        return (methodParameter.getMethodAnnotation(ResponseBodyEncrypt.class) != null);
    }

    @Override
    public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer, NativeWebRequest webRequest) throws Exception {
        System.out.println("进入了handleReturnValue");
        ResponseBodyEncrypt responseBodyEncrypt = returnType.getMethodAnnotation(ResponseBodyEncrypt.class);
        // 设置不需要处理了
        mavContainer.setRequestHandled(true);
        String data = null;
        // 转换数据为Json String格式
        if (returnValue instanceof String){
            data = (String) returnValue;
        }else {
            data = JSON.toJSONString(returnValue);
        }
        //加密
        if (responseBodyEncrypt.encrypt()){
            System.out.println(JSON.toJSONString(returnValue));
            data = Util.strEncrypt(returnValue.toString());
            System.out.println(data);
        }
        HttpServletResponse response = webRequest.getNativeResponse(HttpServletResponse.class);
        response.setContentType("text/plain;charset=utf-8");
        PrintWriter out = response.getWriter();
        out.write("经过HandlerMethodReturnValueHandler加密后的数据:" + data);
        out.flush();
    }
}