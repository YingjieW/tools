package com.tools.ztest.validation;

import javax.validation.constraints.*;
import java.math.BigDecimal;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 17/1/5 下午10:13
 */
public class RequestDTO {

    @NotNull(message = "不能为空")
    @Size(min = 1, max = 12, message = "长度范围:1-12") // 字符长度非字节长度
    private String requestNo;

    // 允许为null,不为null时才会进行校验
    @DecimalMin("0.01")
    @Digits(integer = 12, fraction = 2)
    private BigDecimal amount;

    // 允许为null,不为null时才会进行校验
    @Pattern(regexp = "^(([a-zA-Z0-9_\\.\\-])+\\@(([a-zA-Z0-9\\-])+\\.)+([a-zA-Z0-9]{2,4})+)+$")
    private String email;

    public String getRequestNo() {
        return requestNo;
    }

    public void setRequestNo(String requestNo) {
        this.requestNo = requestNo;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
