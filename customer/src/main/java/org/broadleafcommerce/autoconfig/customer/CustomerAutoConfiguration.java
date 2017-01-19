/*
 * #%L
 * BroadleafCommerce Contact
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
package org.broadleafcommerce.autoconfig.customer;

import org.broadleafcommerce.common.email.service.info.EmailInfo;
import org.broadleafcommerce.common.email.service.info.NullEmailInfo;
import org.broadleafcommerce.common.extensibility.FrameworkXmlBeanDefinitionReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.IOException;

/**
 * @author Philip Baggett (pbaggett)
 */
@Configuration
@ImportResource(value = {
        "classpath:/bl-customer-applicationContext-persistence.xml",
}, reader = FrameworkXmlBeanDefinitionReader.class)
@ComponentScan(basePackages = {
        "org.broadleafcommerce.profile.core"
})
public class CustomerAutoConfiguration {

    @Bean
    public PasswordEncoder blPasswordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public EmailInfo blRegistrationEmailInfo() throws IOException {
        return new NullEmailInfo();
    }

    @Bean
    public EmailInfo blForgotPasswordEmailInfo() throws IOException {
        return new NullEmailInfo();
    }

    @Bean
    public EmailInfo blForgotUsernameEmailInfo() throws IOException {
        return new NullEmailInfo();
    }

    @Bean
    public EmailInfo blChangePasswordEmailInfo() throws IOException {
        return new NullEmailInfo();
    }
}
