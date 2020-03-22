package priv.ymqm.housing.common.advice;


import com.alibaba.fastjson.JSON;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import priv.ymqm.housing.common.annotation.ValidateJson;
import priv.ymqm.housing.common.enums.RestApiResCodeEnum;
import priv.ymqm.housing.common.exception.HousingException;
import priv.ymqm.housing.common.util.ValidatorUtil;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Map;

/**
 * @author chenhonnian
 * @since 2020/03/19
 */
@Aspect
@Component
public class RequestBodyParamCheckAdvice {

    @Pointcut(value = "@annotation(priv.ymqm.housing.common.annotation.ValidateJson) || @annotation(org" +
            ".springframework.web.bind.annotation.PostMapping)")
    public void jsonRequestBody() {
    }


    @Before("jsonRequestBody()")
    public void checkParam(JoinPoint joinPoint) {
        Class<?> group = getValidationGroup(joinPoint);
        Object validateArg = getValidationArgObj(joinPoint);
        if (validateArg == null) {
            return;
        }
        Map<String, String> validateMapResult = ValidatorUtil.validate(validateArg, group);
        if (validateMapResult.size() > 0) {
            String payload = JSON.toJSONString(validateMapResult);
            throw new HousingException(RestApiResCodeEnum.PARAM_ERROR, payload);
        }
    }

    private Class<?> getValidationGroup(JoinPoint joinPoint) {
        Method apiMethod = getRequestMethod(joinPoint);
        ValidateJson validateJsonAnnotation = apiMethod.getDeclaredAnnotation(ValidateJson.class);
        if (validateJsonAnnotation == null) {
            return javax.validation.groups.Default.class;
        }
        Class<?> group = validateJsonAnnotation.group();
        return group;
    }

    private Object getValidationArgObj(JoinPoint joinPoint) {
        Method apiMethod = getRequestMethod(joinPoint);
        Parameter[] parameters = apiMethod.getParameters();
        Object validateArg = null;
        Object[] args = joinPoint.getArgs();
        for (int i = 0; i < parameters.length; i++) {
            Parameter parameter = parameters[i];
            RequestBody requestBodyAnnotation = parameter.getAnnotation(RequestBody.class);
            if (requestBodyAnnotation != null) {
                validateArg = args[i];
                break;
            }
        }
        return validateArg;
    }

    private Method getRequestMethod(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        Object controllerTarget = joinPoint.getTarget();
        Object[] args = joinPoint.getArgs();
        Class<?>[] argTypes = new Class[args.length];
        for (int i = 0; i < args.length; i++) {
            argTypes[i] = args[i].getClass();
        }
        Method apiMethod;
        Class<?> controllerTargetClass = controllerTarget.getClass();
        try {
            apiMethod = controllerTargetClass.getMethod(methodName, argTypes);
        } catch (NoSuchMethodException ex) {
            apiMethod = getMethodByName(controllerTargetClass, methodName);
            if (apiMethod == null) {
                throw new RuntimeException(ex);
            }
        }
        return apiMethod;
    }

    private Method getMethodByName(Class<?> targetClass, String methodName) {
        Method[] methods = targetClass.getMethods();
        for (Method method : methods) {
            if (methodName != null && methodName.equals(method.getName())) {
                return method;
            }
        }
        return null;
    }
}
