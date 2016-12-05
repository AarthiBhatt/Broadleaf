/*
 * #%L
 * BroadleafCommerce Profile
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
import org.broadleafcommerce.core.order.domain.OrderAddress;
import org.broadleafcommerce.core.order.domain.OrderAddressImpl;
import org.springframework.stereotype.Repository;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository("blOrderAddressDao")
public class OrderAddressDaoImpl implements OrderAddressDao {

    @PersistenceContext(unitName = "blPU")
    protected EntityManager em;

    @Resource(name="blEntityConfiguration")
    protected EntityConfiguration entityConfiguration;

    public OrderAddress save(OrderAddress address) {
        return em.merge(address);
    }

    public OrderAddress readOrderAddressById(Long id) {

        return (OrderAddress) em.find(OrderAddressImpl.class, id);
    }

    public OrderAddress create() {
        return (OrderAddress) entityConfiguration.createEntityInstance(OrderAddress.class.getName());
    }

    public void delete(OrderAddress orderAddress) {
        if (!em.contains(orderAddress)) {
            orderAddress = readOrderAddressById(orderAddress.getId());
        }
        em.remove(orderAddress);
    }
}
