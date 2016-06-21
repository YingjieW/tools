package com.tools.enumtype;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 16/5/26 上午11:35
 */
public enum StartTag {

    JSON_CLASS("JsonClass->"),
    JSON_BASE("JsonBase->"),
    CUSTOM_CLASS("CustomClass->");

    private String tag ;

    private StartTag(String tag) {
        this.tag = tag;
    }

    public String getTag() {
        return this.tag;
    }

}
