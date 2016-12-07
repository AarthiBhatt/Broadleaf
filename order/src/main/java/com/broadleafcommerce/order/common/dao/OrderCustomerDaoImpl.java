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
