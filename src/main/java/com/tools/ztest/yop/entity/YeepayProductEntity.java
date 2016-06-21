package com.tools.ztest.yop.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 16/5/18 下午5:46
 */
public class YeepayProductEntity implements Serializable{

    //产品名称：
    private String yeepayProductName;

    //手续费收费模式：
    private String feeRateChargeMethod;

    //手续费率取值：
    private BigDecimal feeRate;

    private Map<String, String> testMap;

    private List<String> testList;

    private Map<String, Integer> testMapInteger;

    private TestEntity testEntity;

    private int assurePeriodInt;

    private Integer assurePeriodInteger;

    public String getYeepayProductName() {
        return yeepayProductName;
    }

    public void setYeepayProductName(String yeepayProductName) {
        this.yeepayProductName = yeepayProductName;
    }

    public String getFeeRateChargeMethod() {
        return feeRateChargeMethod;
    }

    public void setFeeRateChargeMethod(String feeRateChargeMethod) {
        this.feeRateChargeMethod = feeRateChargeMethod;
    }

    public BigDecimal getFeeRate() {
        return feeRate;
    }

    public void setFeeRate(BigDecimal feeRate) {
        this.feeRate = feeRate;
    }

    public Map<String, String> getTestMap() {
        return testMap;
    }

    public void setTestMap(Map<String, String> testMap) {
        this.testMap = testMap;
    }

    public List<String> getTestList() {
        return testList;
    }

    public void setTestList(List<String> testList) {
        this.testList = testList;
    }

    public Map<String, Integer> getTestMapInteger() {
        return testMapInteger;
    }

    public void setTestMapInteger(Map<String, Integer> testMapInteger) {
        this.testMapInteger = testMapInteger;
    }

    public TestEntity getTestEntity() {
        return testEntity;
    }

    public void setTestEntity(TestEntity testEntity) {
        this.testEntity = testEntity;
    }

    public int getAssurePeriodInt() {
        return assurePeriodInt;
    }

    public void setAssurePeriodInt(int assurePeriodInt) {
        this.assurePeriodInt = assurePeriodInt;
    }

    public Integer getAssurePeriodInteger() {
        return assurePeriodInteger;
    }

    public void setAssurePeriodInteger(Integer assurePeriodInteger) {
        this.assurePeriodInteger = assurePeriodInteger;
    }
}
