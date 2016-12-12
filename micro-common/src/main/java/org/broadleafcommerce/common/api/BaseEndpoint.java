package org.broadleafcommerce.common.api;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;

public abstract class BaseEndpoint implements ApplicationContextAware, MessageSourceAware {

    protected ApplicationContext context;

    protected MessageSource messageSource;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

    @Override
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public ApplicationContext getApplicationContext() {
        return this.context;
    }

    public MessageSource getMessageSource() {
        return this.messageSource;
    }
}