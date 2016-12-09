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

import com.broadleafcommerce.order.common.domain.OrderSku;
import com.broadleafcommerce.order.common.domain.OrderSkuImpl;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by brandon on 12/7/16.
 */
@Repository("blOrderSkuDao")
public class OrderSkuDaoImpl {

    @PersistenceContext(unitName = "blPU")
    protected EntityManager em;

    @Resource(name="blEntityConfiguration")
    protected EntityConfiguration entityConfiguration;

    public OrderSku save(OrderSku orderSku) {
        return em.merge(orderSku);
    }

    public OrderSku readOrderSkuById(Long orderSkuId) {
        return (OrderSku) em.find(OrderSkuImpl.class, orderSkuId);
    }

    public OrderSku create() {
        return (OrderSku) entityConfiguration.createEntityInstance(OrderSku.class.getName());
    }

    public void delete(OrderSku orderSku) {
        if (!em.contains(orderSku)) {
            orderSku = readOrderSkuById(orderSku.getId());
        }
        em.remove(orderSku);
    }
}
