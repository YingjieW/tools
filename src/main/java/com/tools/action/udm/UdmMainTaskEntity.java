package com.tools.action.udm;

import open.udm.client.annotation.Column;
import open.udm.client.annotation.Table;
import open.udm.client.entity.BaseMainTaskEntity;

/**
 * Description:
 *
 * @author yingjie.wang
 * @since 17/9/6 下午7:35
 */
@Table(name = "TBL_MAIN_TASK")
public class UdmMainTaskEntity extends BaseMainTaskEntity {

//    @Column(name = "CONTROLLER_ID")
//    private String controllerId;

    @Column(name = "BIZ_TYPE")
    private String bizType;

//    @Column(name = "TEST_DECIMAL")
//    private BigDecimal testDecimal;

//    public String getControllerId() {
//        return controllerId;
//    }
//
//    public void setControllerId(String controllerId) {
//        this.controllerId = controllerId;
//    }

    public String getBizType() {
        return bizType;
    }

    public void setBizType(String bizType) {
        this.bizType = bizType;
    }

//    public BigDecimal getTestDecimal() {
//        return testDecimal;
//    }
//
//    public void setTestDecimal(BigDecimal testDecimal) {
//        this.testDecimal = testDecimal;
//    }
}
