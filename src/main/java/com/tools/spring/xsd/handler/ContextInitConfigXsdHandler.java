package com.tools.spring.xsd.handler;

import com.tools.spring.xsd.parser.PeopleConfigParser;
import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * Description:
 *
 * @author yingjie.wang
 * @since 17/3/23 下午5:27
 */
public class ContextInitConfigXsdHandler extends NamespaceHandlerSupport {

    @Override
    public void init(){
        registerBeanDefinitionParser("people", new PeopleConfigParser());
    }
}
