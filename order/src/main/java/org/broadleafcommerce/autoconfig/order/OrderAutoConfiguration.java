/*
 * #%L
 * BroadleafCommerce Order
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
package org.broadleafcommerce.autoconfig.order;

import org.broadleafcommerce.common.extensibility.FrameworkXmlBeanDefinitionReader;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Scope;

import com.broadleafcommerce.order.common.dto.FulfillmentGroupDTO;
import com.broadleafcommerce.order.common.dto.FulfillmentGroupItemDTO;
import com.broadleafcommerce.order.common.dto.FulfillmentOptionDTO;
import com.broadleafcommerce.order.common.dto.OfferCodeDTO;
import com.broadleafcommerce.order.common.dto.OfferDTO;
import com.broadleafcommerce.order.common.dto.OrderAddressDTO;
import com.broadleafcommerce.order.common.dto.OrderCustomerDTO;
import com.broadleafcommerce.order.common.dto.OrderDTO;
import com.broadleafcommerce.order.common.dto.OrderItemDTO;
import com.broadleafcommerce.order.common.dto.OrderItemDetailDTO;
import com.broadleafcommerce.order.common.dto.OrderPaymentDTO;
import com.broadleafcommerce.order.common.dto.PaymentTransactionDTO;
import com.broadleafcommerce.order.common.dto.SplitFulfillmentGroupDTO;

/**
 * Created by brandon on 12/9/16.
 */
@Configuration
@ImportResource(value = {
        "classpath:/bl-order-applicationContext-persistence.xml",
        "classpath:/bl-order-applicationContext.xml",
        "classpath:/bl-order-applicationContext-workflow.xml"
}, reader = FrameworkXmlBeanDefinitionReader.class)
public class OrderAutoConfiguration {
    
    @Bean(name = "com.broadleafcommerce.order.common.dto.OfferCodeDTO")
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public OfferCodeDTO offerCodeDtoPrototype() {
        return new OfferCodeDTO();
    }
    
    @Bean(name = "com.broadleafcommerce.order.common.dto.OfferDTO")
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public OfferDTO offerDtoPrototype() {
        return new OfferDTO();
    }
    
    @Bean(name = "com.broadleafcommerce.order.common.dto.OrderAddressDTO")
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public OrderAddressDTO orderAddressDtoPrototype() {
        return new OrderAddressDTO();
    }
    
    @Bean(name = "com.broadleafcommerce.order.common.dto.OrderCustomerDTO")
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public OrderCustomerDTO orderCustomerDtoPrototype() {
        return new OrderCustomerDTO();
    }
    
    @Bean(name = "com.broadleafcommerce.order.common.dto.OrderDTO")
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public OrderDTO orderDtoPrototype() {
        return new OrderDTO();
    }
    
    @Bean(name = "com.broadleafcommerce.order.common.dto.OrderItemDetailDTO")
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public OrderItemDetailDTO orderItemDetailDtoPrototype() {
        return new OrderItemDetailDTO();
    }
    
    @Bean(name = "com.broadleafcommerce.order.common.dto.OrderItemDTO")
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public OrderItemDTO orderItemDtoPrototype() {
        return new OrderItemDTO();
    }
    
    @Bean(name = "com.broadleafcommerce.order.common.dto.OrderPaymentDTO")
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public OrderPaymentDTO orderPaymentDtoPrototype() {
        return new OrderPaymentDTO();
    }
    
    @Bean(name = "com.broadleafcommerce.order.common.dto.PaymentTransactionDTO")
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public PaymentTransactionDTO paymentTransactionDtoPrototype() {
        return new PaymentTransactionDTO();
    }
    
    @Bean(name = "com.broadleafcommerce.order.common.dto.FulfillmentGroupDTO")
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public FulfillmentGroupDTO fulfillmentGroupDtoPrototype() {
        return new FulfillmentGroupDTO();
    }
    
    @Bean(name = "com.broadleafcommerce.order.common.dto.FulfillmentOptionDTO")
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public FulfillmentOptionDTO fulfillmentOptionDtoPrototype() {
        return new FulfillmentOptionDTO();
    }
    
    @Bean(name = "com.broadleafcommerce.order.common.dto.SplitFulfillmentGroupDTO")
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public SplitFulfillmentGroupDTO splitFulfillmentGroupDtoPrototype() {
        return new SplitFulfillmentGroupDTO();
    }
    
    @Bean(name = "com.broadleafcommerce.order.common.dto.FulfillmentGroupItemDTO")
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public FulfillmentGroupItemDTO fulfillmentGroupItemDtoPrototype() {
        return new FulfillmentGroupItemDTO();
    }
}
