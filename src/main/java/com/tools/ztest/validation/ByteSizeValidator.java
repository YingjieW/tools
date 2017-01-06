package com.tools.ztest.validation;

import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 17/1/6 下午12:18
 */
public class ByteSizeValidator implements ConstraintValidator<ByteSize, String> {

    private int min;
    private int max;
    private String charset;

    @Override
    public void initialize(ByteSize byteSize) {
        this.min = byteSize.min();
        this.max = byteSize.max();
        this.charset = StringUtils.isNotBlank(byteSize.charset()) ? byteSize.charset() : Charset.defaultCharset().toString();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (StringUtils.isNotBlank(value)) {
            try {
                int byteLengh = value.getBytes(charset).length;
                if (byteLengh < min || byteLengh > max) {
                    return false;
                }
            } catch (UnsupportedEncodingException e) {
                // 禁用默认的message的值
                context.disableDefaultConstraintViolation();
                // 重新添加错误提示语句
                context.buildConstraintViolationWithTemplate("注解ByteSize使用有误,编码格式["+charset+"]不支持").addConstraintViolation();
                return false;
            }
        }
        // 当min>0时,不允许为空
        return (min <= 0);
    }
}
