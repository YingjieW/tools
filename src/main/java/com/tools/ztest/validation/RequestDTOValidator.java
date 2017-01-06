package com.tools.ztest.validation;

import java.math.BigDecimal;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 17/1/5 下午10:38
 */
public class RequestDTOValidator {
    public static void main(String[] args) throws Exception {
        RequestDTO requestDTO = new RequestDTO();
        requestDTO.setRequestNo("1234567890123");
        requestDTO.setAmount(new BigDecimal("1.03"));
        requestDTO.setEmail("yingjie.wang@yeepay.com");
        requestDTO.setProductName("一二三四五");
        BeanValidator.validate(requestDTO);
    }
}
