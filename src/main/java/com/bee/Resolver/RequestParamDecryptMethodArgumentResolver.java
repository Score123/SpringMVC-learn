package com.bee.Resolver;

import com.bee.annotation.RequestParamDecrypt;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @ClassName RequestParamDecryptMethodArgumentResolver
 * @Description TODO
 * @Author ChenLiLin
 * @Date 2018/9/27 16:29
 * @Version 1.0
 **/
public class RequestParamDecryptMethodArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(RequestParamDecrypt.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        System.out.println("requesparam======  进来了");
        // 获取元素的类型
        Class<?> paramType = parameter.getParameterType();
        // 获取全部表单参数
        Map<String, String[]> parameterMap = webRequest.getParameterMap();
        // 获取注解对象RequestParamDecrypt
        RequestParamDecrypt parameterAnnotation = parameter.getParameterAnnotation(RequestParamDecrypt.class);
        // 返回结果
        String key;
        String vaule;
        Map<String, String> result = new LinkedHashMap<String, String>(parameterMap.size());
        for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
            if (entry.getValue().length > 0) {
                if (parameterAnnotation.keyDecrypt()) {
                    key = entry.getKey()+"解密";
                    vaule = entry.getValue()[0]+"解密";
                    result.put(key, vaule);
                }else{
                    key = entry.getKey();
                    vaule = entry.getValue()[0]+"解密";
                    result.put(key, vaule);
                }

            }
        }
        // 获取属性名称
        String parameterName = parameter.getParameterName();
        return result.get(parameterName);
    }
}