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
