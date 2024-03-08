package com.litian.dancechar.framework.common.validator;

import com.litian.dancechar.framework.common.exception.CheckedException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

/**
 * 类描述: hibernate-validator校验工具类
 *
 * @author 01406831
 * @date 2021/03/31 16:56
 */
public class DCValidatorUtil {
    private static Validator validator;

    static {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    /**
     * 校验对象
     * @param object 待校验对象
     * @param groups 待校验的组
     * @throws CheckedException 校验不通过，则报RRException异常
     */
    public static void validateModel(Object object, Class<?>... groups) throws CheckedException {
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(object, groups);
        if (!constraintViolations.isEmpty()) {
            throw new ConstraintViolationException(constraintViolations);
        }
    }
}
