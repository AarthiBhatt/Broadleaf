/*
 * #%L
 * BroadleafCommerce Framework
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
package org.broadleafcommerce.core.order.service;

import org.apache.commons.lang3.StringUtils;
import org.broadleafcommerce.core.order.domain.Order;
import org.broadleafcommerce.core.order.service.call.MergeCartResponse;
import org.broadleafcommerce.core.order.service.exception.RemoveFromCartException;
import org.broadleafcommerce.core.order.service.type.OrderStatus;
import org.broadleafcommerce.core.pricing.service.exception.PricingException;
import org.broadleafcommerce.profile.core.domain.Customer;
import org.springframework.stereotype.Service;

import com.broadleafcommerce.order.common.domain.OrderCustomer;

import java.util.List;

import javax.annotation.Resource;

@Service("blOrderCustomerFacadeService")
public class OrderCustomerFacadeServiceImpl implements OrderCustomerFacadeService {

    @Resource(name = "blOrderCustomerService")
    protected OrderCustomerService orderCustomerService;
    
    @Resource(name = "blOrderService")
    protected OrderService orderService;
    
    @Resource(name = "blMergeCartService")
    protected MergeCartService mergeCartService;
    
    @Override
    public List<Order> findOrdersForCustomer(Customer customer, OrderStatus orderStatus) {
        OrderCustomer orderCustomer = findOrderCustomerFromCustomer(customer);
        if (orderCustomer == null) {
            return null;
        }
        return orderService.findOrdersForCustomer(orderCustomer, orderStatus);
    }

    @Override
    public Order findCartForCustomer(Customer customer) {
        OrderCustomer orderCustomer = findOrderCustomerFromCustomer(customer);
        if (orderCustomer == null) {
            return null;
        }
        return orderService.findCartForCustomer(orderCustomer);
    }

    @Override
    public Order findNamedOrderForCustomer(String name, Customer customer) {
        OrderCustomer orderCustomer = findOrderCustomerFromCustomer(customer);
        if (orderCustomer == null) {
            return null;
        }
        return orderService.findNamedOrderForCustomer(name, orderCustomer);
    }
    
    @Override
    public MergeCartResponse mergeCart(Customer customer, Order order) throws PricingException, RemoveFromCartException {
        OrderCustomer orderCustomer = findOrderCustomerFromCustomer(customer);
        if (orderCustomer == null) {
            return null;
        }
        return mergeCartService.mergeCart(orderCustomer, order);
    }
    
    @Override
    public Order createNamedOrderForCustomer(String name, Customer customer) {
        OrderCustomer orderCustomer = createOrderCustomerFromCustomer(customer);
        if (orderCustomer == null) {
            return null;
        }
        return orderService.createNamedOrderForCustomer(name, orderCustomer);
    }
    
    @Override
    public Order createNewCartForCustomer(Customer customer) {
        OrderCustomer orderCustomer = createOrderCustomerFromCustomer(customer);
        if (orderCustomer == null) {
            return null;
        }
        return orderService.createNewCartForCustomer(orderCustomer);
    }
    
    protected OrderCustomer findOrderCustomerFromCustomer(Customer customer) {
        if (customer == null || customer.getId() == null) {
            return null;
        }
        return orderCustomerService.findOrderCustomerByExternalId(customer.getId());
    }
    
    protected OrderCustomer createOrderCustomerFromCustomer(Customer customer) {
        if (customer == null || customer.getId() == null) {
            return null;
        }
        OrderCustomer orderCustomer = orderCustomerService.createOrderCustomer();
        if (StringUtils.isNotBlank(customer.getEmailAddress())) {
            orderCustomer.setEmailAddress(customer.getEmailAddress());
        }
        if (StringUtils.isNotBlank(customer.getFirstName())) {
            orderCustomer.setFirstName(customer.getFirstName());
        }
        if (StringUtils.isNoneBlank(customer.getLastName())) {
            orderCustomer.setLastName(customer.getLastName());
        }
        orderCustomer.setExternalId(customer.getId());
        return orderCustomer = orderCustomerService.save(orderCustomer);
    }
    
}
