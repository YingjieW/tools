package com.tools.ztest.validation;

import com.yeepay.g3.utils.common.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 17/1/6 下午4:50
 */
public class PatternOfStringValidator implements ConstraintValidator<PatternOfString,String> {

    private String mustContainText;

    @Override
    public void initialize(PatternOfString constraintAnnotation) {
        this.mustContainText = constraintAnnotation.mustContainText();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (StringUtils.isNotBlank(mustContainText) && value != null) {
            if (StringUtils.isBlank(value)) {
                return false;
            }
            return value.contains(mustContainText);
        }
        return true;
    }
}
