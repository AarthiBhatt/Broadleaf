package com.broadleafcommerce.order.common.service;

import org.springframework.stereotype.Service;

import com.broadleafcommerce.order.common.dao.OrderCustomerDao;
import com.broadleafcommerce.order.common.domain.OrderCustomer;

import javax.annotation.Resource;

/**
 * Created by brandon on 12/7/16.
 */
@Service("blOrderCustomerService")
public class OrderCustomerServiceImpl implements OrderCustomerService {

    @Resource(name = "blOrderCustomerDao")
    OrderCustomerDao orderCustomerDao;

    @Override
    public OrderCustomer saveOrderCustomer(OrderCustomer orderCustomer) {
        return orderCustomerDao.save(orderCustomer);
    }

    @Override
    public OrderCustomer readOrderCustomerById(Long orderCustomerId) {
        return orderCustomerDao.readOrderCustomerById(orderCustomerId);
    }

    @Override
    public OrderCustomer create() {
        return orderCustomerDao.create();
    }

    @Override
    public void delete(OrderCustomer orderCustomer) {
        orderCustomerDao.delete(orderCustomer);
    }
}
