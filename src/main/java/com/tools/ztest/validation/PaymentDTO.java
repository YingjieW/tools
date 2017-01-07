package com.tools.ztest.validation;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 17/1/6 下午9:42
 */
public class PaymentDTO {

    @NotBlank
    private String payOrderNo;

    public String getPayOrderNo() {
        return payOrderNo;
    }

    public void setPayOrderNo(String payOrderNo) {
        this.payOrderNo = payOrderNo;
    }
}
