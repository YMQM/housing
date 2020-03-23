package priv.ymqm.housing.common.util;

import org.springframework.util.ReflectionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @author chenhonnian
 * @since 2020/03/23
 */
public class ReflectUtil {
    private ReflectUtil() {
    }

    public static Method[] findMethodsWithAnnotation(Class<?> targetCls, Class<? extends Annotation> annotationType) {
        Method[] methods = ReflectionUtils.getAllDeclaredMethods(targetCls);
        List<Method> matchMethods = new ArrayList<>();
        for (Method method : methods) {
            Annotation annotation = method.getAnnotation(annotationType);
            if (annotation != null) {
                matchMethods.add(method);
            }
        }
        return matchMethods.toArray(new Method[0]);
    }
}
