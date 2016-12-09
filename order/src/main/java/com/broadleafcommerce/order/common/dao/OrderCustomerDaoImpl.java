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
package com.broadleafcommerce.order.common.dao;

import org.broadleafcommerce.common.persistence.EntityConfiguration;
import org.springframework.stereotype.Repository;

import com.broadleafcommerce.order.common.domain.OrderCustomer;
import com.broadleafcommerce.order.common.domain.OrderCustomerImpl;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by brandon on 12/7/16.
 */
@Repository("blOrderCustomerDao")
public class OrderCustomerDaoImpl implements OrderCustomerDao {

    @PersistenceContext(unitName = "blPU")
    protected EntityManager em;

    @Resource(name="blEntityConfiguration")
    protected EntityConfiguration entityConfiguration;

    public OrderCustomer save(OrderCustomer orderCustomer) {
        return em.merge(orderCustomer);
    }

    public OrderCustomer readOrderCustomerById(Long orderCustomerId) {
        return (OrderCustomer) em.find(OrderCustomerImpl.class, orderCustomerId);
    }

    public OrderCustomer create() {
        return (OrderCustomer) entityConfiguration.createEntityInstance(OrderCustomer.class.getName());
    }

    public void delete(OrderCustomer orderCustomer) {
        if (!em.contains(orderCustomer)) {
            orderCustomer = readOrderCustomerById(orderCustomer.getId());
        }
        em.remove(orderCustomer);
    }
}
