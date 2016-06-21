package com.tools.ztest.yop.entity;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 16/5/26 上午10:41
 */
public class TestEntity {

    private String testName;

    private int testAge;

    private float testScore;

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public int getTestAge() {
        return testAge;
    }

    public void setTestAge(int testAge) {
        this.testAge = testAge;
    }

    public float getTestScore() {
        return testScore;
    }

    public void setTestScore(float testScore) {
        this.testScore = testScore;
    }

    @Override
    public String toString() {
        return "TestEntity{" +
                "testName='" + testName + '\'' +
                ", testAge=" + testAge +
                ", testScore=" + testScore +
                '}';
    }
}
