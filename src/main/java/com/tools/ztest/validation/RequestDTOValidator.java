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
//        requestDTO.setRequestNo("1234567890");
        requestDTO.setRequestNo("一二三四五六七八九十");
        requestDTO.setAmount(new BigDecimal("1.035"));
        requestDTO.setEmail("yingjie.wang@yeepay.com");
        BeanValidator.validate(requestDTO);
    }
}