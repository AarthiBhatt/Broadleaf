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
package org.broadleafcommerce.core.order.dao;

import org.broadleafcommerce.common.persistence.EntityConfiguration;
import org.broadleafcommerce.core.order.domain.OrderCustomer;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@Repository("blOrderCustomerDao")
public class OrderCustomerDaoImpl implements OrderCustomerDao {

    @PersistenceContext(unitName = "blPU")
    protected EntityManager em;

    @Resource(name="blEntityConfiguration")
    protected EntityConfiguration entityConfiguration;
    
    @Override
    public OrderCustomer readOrderCustomerByExternalId(Long externalId) {
        TypedQuery<OrderCustomer> q = em.createQuery("SELECT c FROM " + OrderCustomer.class.getName() + "c WHERE c.externalId = :externalId", OrderCustomer.class);
        q.setParameter("externalId", externalId);
        return q.getSingleResult();
    }

    @Override
    public OrderCustomer createOrderCustomer() {
        return (OrderCustomer) entityConfiguration.createEntityInstance(OrderCustomer.class.getName());
    }

    @Override
    public OrderCustomer save(OrderCustomer orderCustomer) {
        return em.merge(orderCustomer);
    }
}
