package priv.ymqm.housing.common.util;


import com.alibaba.fastjson.JSON;
import priv.ymqm.housing.common.enums.RestApiResCodeEnum;
import priv.ymqm.housing.common.exception.HousingException;

import javax.validation.ConstraintViolation;
import javax.validation.Path;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.*;

/**
 * hibernate-validator校验工具类
 * * <p>
 * * 参考文档：http://docs.jboss.org/hibernate/validator/5.4/reference/en-US/html_single/
 *
 * @author chenhonnian
 * @since 2020/03/20
 */
public class ValidatorUtil {
    private static Validator validator;

    static {
        Locale.setDefault(new Locale("zh", "CN"));
        validator = Validation.buildDefaultValidatorFactory().getValidator();

    }

    private ValidatorUtil() {
    }

    public static void validateEntity(Object object, Class<?>... groups) {
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(object, groups);
        if (!constraintViolations.isEmpty()) {
            Map<String, String> validationResult = getValidationResult(constraintViolations);
            String payLoad = JSON.toJSONString(validationResult);
            throw new HousingException(RestApiResCodeEnum.PARAM_ERROR, payLoad);
        }
    }

    public static Map<String, String> validate(Object object, Class<?>... groups) {
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(object, groups);
        if (constraintViolations.isEmpty()) {
            return Collections.emptyMap();
        }
        Map<String, String> result = getValidationResult(constraintViolations);
        return result;
    }

    private static String getFieldName(ConstraintViolation<Object> constraintViolation) {
        Path path = constraintViolation.getPropertyPath();
        String fieldName = path.toString();
        return fieldName;
    }

    private static Map<String, String> getValidationResult(Set<ConstraintViolation<Object>> constraintViolations) {
        Map<String, String> result = new HashMap<>(constraintViolations.size());
        for (ConstraintViolation<Object> constraintViolation : constraintViolations) {
            String fieldName = getFieldName(constraintViolation);
            String message = constraintViolation.getMessage();
            result.put(fieldName, message);
        }
        return result;
    }


}

