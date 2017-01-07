package com.tools.ztest.design.builder;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 17/1/7 下午11:37
 */
public class PersonDirector {

    private PersonBuilder personBuilder;

    public PersonDirector(PersonBuilder personBuilder) {
        this.personBuilder = personBuilder;
    }

    public void createPerson() {
        personBuilder.buildHead();
        personBuilder.buildBody();
        personBuilder.buildArmLeft();
        personBuilder.buildArmRight();
        personBuilder.buildLegleft();
        personBuilder.buildLegRight();
    }
}
