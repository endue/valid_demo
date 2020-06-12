package com.simon.aop;

import com.alibaba.fastjson.JSON;
import com.simon.util.ErrorCode;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.hibernate.validator.internal.engine.ValidatorImpl;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.annotation.Resource;
import javax.validation.ConstraintViolation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @data: 2020/6/10 17:35
 * @author: simon
 * @version: 参数校验拦截器,校验参数合法性
 * @description:
 */
@Component
@Aspect
@Order(1)
public class ValidationHandler {

    @Resource(name = "validator")
    public LocalValidatorFactoryBean validator;

    @Around(value = "execution(* com.simon..*.*(..))&&@annotation(javax.validation.Valid)")
    private Object validateAround(ProceedingJoinPoint joinPoint) throws Throwable {

        Map<String, Object> result = new HashMap<>();

        /**
         * 默认校验值为true
         */
        result.put("validate", true);

        boolean flag = true;

        /**
         * 所有的请求参数
         */
        Object[] args = null;
        Method method = null;
        Object target = null;
        String methodName = null;
        try {

            /**
             * 获取被切方法名字
             */
            methodName = joinPoint.getSignature().getName();
            target = joinPoint.getTarget();

            /**
             * 得到拦截的方法
             */
            method = getMethodByClassAndName(target.getClass(), methodName);

            /**
             * 方法的参数
             */
            args = joinPoint.getArgs();
            Class<?>[] groups = new Class<?>[0];
            LocalValidatorFactoryBean validatorFactoryBean = validator;
            ValidatorImpl validatorImpl = (ValidatorImpl) validatorFactoryBean.getValidator();

            /**
             * 通过javax定义的校验规则，进行注解校验
             */
            Set<ConstraintViolation<Object>> violations = validatorImpl.validateParameters(target, method, args, groups);
            /**
             * 存在校验不合法的情况
             */
            if (!violations.isEmpty()) {
                for (Iterator<ConstraintViolation<Object>> iterator = violations.iterator(); iterator.hasNext();) {
                    ConstraintViolation<Object> constraintViolation = iterator.next();
                    String errorMessage = constraintViolation.getMessage();
                    String field = constraintViolation.getPropertyPath().toString().split("\\.")[1];
                    try {
                        String _errorMessage = ErrorCode.toErrorMessage(errorMessage, field);
                        result.put("validate", false);
                        result.put("result", _errorMessage);
                        break;
                    } catch (Exception e) {
                        String _errorMessage = this.exception(e, methodName);
                        result.put("validate", false);
                        result.put("result", _errorMessage);
                        break;
                    }
                }
            }
        } catch (Exception e) {
            String _errorMessage = this.exception(e, methodName);
            result.put("validate", false);
            result.put("result", _errorMessage);
        } finally {
            flag = (boolean) result.get("validate");
            if (flag) {
                /**
                 * 校验正确,执行controller
                 */
                return joinPoint.proceed();
            } else {
                return result.get("result");
            }
        }
    }


    private Method getMethodByClassAndName(@SuppressWarnings("rawtypes") Class c, String methodName) {
        Method[] methods = c.getDeclaredMethods();
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                return method;
            }
        }
        return null;
    }

    private String exception(Exception e, String methodName) {
        Map<String, String> map = new HashMap<>();
        map.put("code", ErrorCode.EXCEPTION_CODE);
        map.put("message", ErrorCode.getErrorMessage(ErrorCode.EXCEPTION_CODE) + e);
        return JSON.toJSONString(map);
    }
}
