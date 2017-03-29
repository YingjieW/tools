package com.tools.spring.xsd.parser;

import com.alibaba.fastjson.JSON;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.config.AdvisorComponentDefinition;
import org.springframework.aop.config.AdvisorEntry;
import org.springframework.aop.config.AopNamespaceUtils;
import org.springframework.aop.support.DefaultBeanFactoryPointcutAdvisor;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.RuntimeBeanNameReference;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.parsing.CompositeComponentDefinition;
import org.springframework.beans.factory.parsing.ParseState;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;

/**
 * Description: 可参考spring-aop包中的ConfigBeanDefinitionParser类的实现
 *
 * @author yingjie.wang
 * @since 17/3/29 下午4:24
 */
public class TestParser implements BeanDefinitionParser {
    /**
     * 切入点属性
     */
    protected static final String POINTCUT = "pointcut";

    protected String adviceRef;

    protected static final String ADVICE_BEAN_NAME = "adviceBeanName";

    protected static final String ID = "id";

    protected static final String EXPRESSION = "expression";

    protected ParseState parseState = new ParseState();

//    protected abstract void setAdviceRef();

    public String getAdviceRef() {
        return this.adviceRef;
    }

    protected void setAdviceRef(String ref) {
        this.adviceRef = ref;
    }

    @Override
    public BeanDefinition parse(Element element, ParserContext parserContext) {

        System.out.println("=====> element:" + JSON.toJSONString(element));
        System.out.println("=====> parserContext:" + JSON.toJSONString(parserContext));

//        setAdviceRef();

        CompositeComponentDefinition compositeDef = new CompositeComponentDefinition(element.getTagName(), parserContext.extractSource(element));
        parserContext.pushContainingComponent(compositeDef);

        configureAutoProxyCreator(parserContext, element);

        String pointcut = element.getAttribute(POINTCUT);

        if (StringUtils.isEmpty(pointcut)) {
            parserContext.getReaderContext().error("'pointcut' attribute contains empty value.", this.parseState.snapshot());
        }

        parseAdvisor(element, parserContext);
        return null;
    }

    protected void configureAutoProxyCreator(ParserContext parserContext, Element element) {
        AopNamespaceUtils.registerAspectJAutoProxyCreatorIfNecessary(parserContext, element);
    }

    protected void parseAdvisor(Element advisorElement, ParserContext parserContext) {
        AbstractBeanDefinition advisorDef = createAdvisorBeanDefinition(advisorElement, parserContext);
        String id = advisorElement.getAttribute(ID);
        try {
            this.parseState.push(new AdvisorEntry(id));
            String advisorBeanName = id;
            if (StringUtils.hasText(advisorBeanName)) {
                parserContext.getRegistry().registerBeanDefinition(advisorBeanName, advisorDef);
            } else {
                advisorBeanName = parserContext.getReaderContext().registerWithGeneratedName(advisorDef);
            }

            Object pointcut = parsePointcutProperty(advisorElement, parserContext);
            if (pointcut instanceof BeanDefinition) {
                advisorDef.getPropertyValues().add(POINTCUT, pointcut);
                parserContext.registerComponent(new AdvisorComponentDefinition(advisorBeanName, advisorDef, (BeanDefinition) pointcut));
            } else if (pointcut instanceof String) {
                advisorDef.getPropertyValues().add(POINTCUT, new RuntimeBeanReference((String) pointcut));
                parserContext.registerComponent(new AdvisorComponentDefinition(advisorBeanName, advisorDef));
            }
        } finally {
            this.parseState.pop();
        }
    }

    protected AbstractBeanDefinition createAdvisorBeanDefinition(Element advisorElement, ParserContext parserContext) {
        RootBeanDefinition advisorDefinition = new RootBeanDefinition(DefaultBeanFactoryPointcutAdvisor.class);
        advisorDefinition.setSource(parserContext.extractSource(advisorElement));
        advisorDefinition.getPropertyValues().add(ADVICE_BEAN_NAME, new RuntimeBeanNameReference(getAdviceRef()));
        return advisorDefinition;
    }

    protected Object parsePointcutProperty(Element element, ParserContext parserContext) {
        String expression = element.getAttribute(POINTCUT);
        AbstractBeanDefinition pointcutDefinition = createPointcutDefinition(expression);
        pointcutDefinition.setSource(parserContext.extractSource(element));
        return pointcutDefinition;
    }

    protected AbstractBeanDefinition createPointcutDefinition(String expression) {
        RootBeanDefinition beanDefinition = new RootBeanDefinition(AspectJExpressionPointcut.class);
        beanDefinition.setScope(BeanDefinition.SCOPE_PROTOTYPE);
        beanDefinition.setSynthetic(true);
        beanDefinition.getPropertyValues().add(EXPRESSION, expression);
        return beanDefinition;
    }
}
