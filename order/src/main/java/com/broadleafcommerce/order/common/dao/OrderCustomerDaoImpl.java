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

import org.apache.commons.collections.CollectionUtils;
import org.broadleafcommerce.common.persistence.EntityConfiguration;
import org.springframework.stereotype.Repository;

import com.broadleafcommerce.order.common.domain.OrderCustomer;
import com.broadleafcommerce.order.common.domain.OrderCustomerImpl;

import java.util.List;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Created by brandon on 12/7/16.
 */
@Repository("blOrderCustomerDao")
public class OrderCustomerDaoImpl implements OrderCustomerDao {

    @PersistenceContext(unitName = "blPU")
    protected EntityManager em;

    @Resource(name="blEntityConfiguration")
    protected EntityConfiguration entityConfiguration;

    @Override
    public OrderCustomer save(OrderCustomer orderCustomer) {
        return em.merge(orderCustomer);
    }

    @Override
    public OrderCustomer readOrderCustomerById(Long orderCustomerId) {
        return (OrderCustomer) em.find(OrderCustomerImpl.class, orderCustomerId);
    }
    
    @Override
    public OrderCustomer readOrderCustomerByExternalId(Long externalId) {
        Query q = em.createQuery("SELECT c FROM com.broadleafcommerce.order.common.domain.OrderCustomer c WHERE c.externalId = :id", OrderCustomerImpl.class);
        q.setParameter("id", externalId);
        List<OrderCustomer> results = q.getResultList();
        if (CollectionUtils.isNotEmpty(results)) {
            return results.get(0);
        }
        return null;
    }

    @Override
    public OrderCustomer create() {
        return (OrderCustomer) entityConfiguration.createEntityInstance(OrderCustomer.class.getName());
    }

    @Override
    public void delete(OrderCustomer orderCustomer) {
        if (!em.contains(orderCustomer)) {
            orderCustomer = readOrderCustomerById(orderCustomer.getId());
        }
        em.remove(orderCustomer);
    }
}
