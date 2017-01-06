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
    @Size(min = 1, max = 50, message = "长度超限:1-50") // 字符长度而非字节长度
    private String requestNo;

    // 允许为null,不为null时才会进行校验
    @DecimalMin("0.01")
    @Digits(integer = 12, fraction = 2, message = "必须大于0,最多支持两位小数")
    private BigDecimal amount;

    // 允许为null,不为null时才会进行校验
    @Pattern(regexp = "^(([a-zA-Z0-9_\\.\\-])+\\@(([a-zA-Z0-9\\-])+\\.)+([a-zA-Z0-9]{2,4})+)+$", message = "邮箱格式有误")
    private String email;

    @ByteSize(max = 10, charset = "UTF-8", message = "长度超限,最长10个字节")
    private String productName;

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

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}
