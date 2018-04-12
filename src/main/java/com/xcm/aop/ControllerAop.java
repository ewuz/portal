package com.xcm.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 用于ajax请求
 * 返回参数json格式的动态增强
 *
 */
@Aspect
@Component
public class ControllerAop {
    private final static Logger logger = LoggerFactory.getLogger(ControllerAop.class);
    /**
     * 相应json返回格式切点,返回格式增强
     * 该切点只会拦截有ResponseBody注解的方法
     */
    @Pointcut("@annotation(org.springframework.web.bind.annotation.ResponseBody)")
    public void responseBodyDataToMap() {
    }

    /**
     * 相应json返回格式切点,返回格式增强
     * 该切点拦截类上有@RestController注解下的所有方法
     */
    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    public void restControllerDataToMap() {
    }

    @Pointcut("execution(public * com.xcm.controller..*.report(..))")
    public void report(){}
    /**
     * controller 返回json格式的动态增强,转换为map
     *
     */
    @Around("responseBodyDataToMap() || restControllerDataToMap()")
    public Object controllerAfter (ProceedingJoinPoint joinPoint) {
        Map<String, Object> result = new HashMap<>();
        try {
            Object proceed = joinPoint.proceed();
            if (proceed instanceof Exception) {
                Exception ex = (Exception) proceed;
                result.put("code", 1);
                result.put("msg", ex.getMessage());
                result.put("data", null);
            } else {
                result.put("code", 0);
                result.put("msg", "操作成功");
                result.put("data", proceed);
            }
            return result;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            logger.error("aop for controller transform data to json error", throwable);
            result.put("code", -1);
            result.put("msg","系统错误，请联系管理员");
            result.put("data",null);
            return result;
        }
    }

}