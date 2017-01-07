package com.tools.ztest.design.builder;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 17/1/7 下午11:38
 */
public class Client {
    public static void main(String[] args) throws Exception {
        ThinPersionBuilder thinPerson = new ThinPersionBuilder();
        PersonDirector personDirectorThin = new PersonDirector(thinPerson);
        FatPersionbuilder fatPersion = new FatPersionbuilder();
        PersonDirector personDirectorFat = new PersonDirector(fatPersion);
        personDirectorThin.createPerson();
        personDirectorFat.createPerson();
    }
}
