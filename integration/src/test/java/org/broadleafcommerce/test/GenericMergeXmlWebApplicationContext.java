/*
 * #%L
 * BroadleafCommerce Integration
 * %%
 * Copyright (C) 2009 - 2016 Broadleaf Commerce
 * %%
 * Licensed under the Broadleaf Fair Use License Agreement, Version 1.0
 * (the "Fair Use License" located  at http://license.broadleafcommerce.org/fair_use_license-1.0.txt)
 * unless the restrictions on use therein are violated and require payment to Broadleaf in which case
 * the Broadleaf End User License Agreement (EULA), Version 1.1
 * (the "Commercial License" located at http://license.broadleafcommerce.org/commercial_license-1.1.txt)
 * shall apply.
 * 
 * Alternatively, the Commercial License may be replaced with a mutually agreed upon license (the "Custom License")
 * between you and Broadleaf Commerce. You may not use this file except in compliance with the applicable license.
 * #L%
 */
package org.broadleafcommerce.test;

import org.broadleafcommerce.common.web.extensibility.MergeXmlWebApplicationContext;
import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;

/**
 * Created by brandon on 11/1/16.
 */
public class GenericMergeXmlWebApplicationContext extends MergeXmlWebApplicationContext implements BeanDefinitionRegistry {
    @Override
    public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) throws BeanDefinitionStoreException {
        getDefaultListableBeanFactory().registerBeanDefinition(beanName, beanDefinition);
    }

    @Override
    public void removeBeanDefinition(String beanName) throws NoSuchBeanDefinitionException {
        getDefaultListableBeanFactory().removeBeanDefinition(beanName);
    }

    @Override
    public BeanDefinition getBeanDefinition(String beanName) throws NoSuchBeanDefinitionException {
        return getDefaultListableBeanFactory().getBeanDefinition(beanName);
    }

    @Override
    public boolean isBeanNameInUse(String beanName) {
        return getDefaultListableBeanFactory().isBeanNameInUse(beanName);
    }

    @Override
    public void registerAlias(String s, String s1) {
        getDefaultListableBeanFactory().registerAlias(s, s1);
    }

    @Override
    public void removeAlias(String s) {
        getDefaultListableBeanFactory().removeAlias(s);
    }

    @Override
    public boolean isAlias(String s) {
        return getDefaultListableBeanFactory().isAlias(s);
    }

    public final DefaultListableBeanFactory getDefaultListableBeanFactory() {
        if (!DefaultListableBeanFactory.class.isAssignableFrom(getBeanFactory().getClass())) {
            throw new IllegalStateException("BeanFactory is not an instance of DefaultListableBeanFactory, " +
                    "GenericMergeXmlWebApplicationContext needs an instance of DefaultListableBeanFactory " +
                    "to properly implement the BeanDefinitionRegistry interface");
        }
        return (DefaultListableBeanFactory) getBeanFactory();
    }
}
