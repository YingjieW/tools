package com.tools.ztest.validation;

import org.apache.commons.collections.CollectionUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 17/1/5 下午10:51
 */
public class BeanValidator {

    private static Validator validator;

    static {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    public static <E> void validate(Object obj) {
        if (obj == null) {
            throw new IllegalArgumentException("Param must be specified.");
        }
        Object[] parameters = obj.getClass().isArray() ? (Object[]) obj : new Object[] {obj};
        for (Object param : parameters) {
            Set<ConstraintViolation<E>> set = validator.validate((E) param);
            if (CollectionUtils.isNotEmpty(set)) {
                throw new IllegalArgumentException(getMergedMessage(set));
            }
        }
    }

    public static String getMergedMessage(Set set) {
        if (set == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder("");
        ConstraintViolation constraintViolation = null;
        for (Object obj : set) {
            if (obj instanceof ConstraintViolation && obj != null) {
                constraintViolation = (ConstraintViolation) obj;
                sb.append("[").append(constraintViolation.getPropertyPath()).append("]");
                sb.append(constraintViolation.getMessage());
            }
        }
        return sb.toString();
    }
}
