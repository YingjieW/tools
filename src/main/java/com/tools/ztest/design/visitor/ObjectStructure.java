package com.tools.ztest.design.visitor;

import java.util.LinkedList;
import java.util.List;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 17/2/28 下午12:04
 */
public class ObjectStructure {
    List<Element> elements = new LinkedList<Element>();

    public void attach(Element element) {
        elements.add(element);
    }

    public void detach(Element element) {
        elements.remove(element);
    }

    public void accept(Visitor visitor) {
        for (Element element : elements) {
            element.accept(visitor);
        }
    }
}
