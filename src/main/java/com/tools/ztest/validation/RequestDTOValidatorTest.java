package com.tools.ztest.validation;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 17/1/5 下午10:38
 */
public class RequestDTOValidatorTest {
    public static void main(String[] args) throws Exception {
        RequestDTO requestDTO = new RequestDTO();
        requestDTO.setMemo("Hello world");
        requestDTO.setCallbackUrl("www.yeepay.com");
        PaymentDTO paymentDTO = new PaymentDTO();
        requestDTO.setPaymentDTO(paymentDTO);

        BeanValidator.validate(requestDTO);
    }
}

