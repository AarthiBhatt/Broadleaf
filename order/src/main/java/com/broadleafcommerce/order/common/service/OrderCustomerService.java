package com.broadleafcommerce.order.common.service;

import com.broadleafcommerce.order.common.domain.OrderCustomer;

/**
 * Created by brandon on 12/7/16.
 */
public interface OrderCustomerService {

    public OrderCustomer saveOrderCustomer(OrderCustomer orderCustomer);

    public OrderCustomer readOrderCustomerById(Long orderCustomerId);

    public OrderCustomer create();

    public void delete(OrderCustomer orderCustomer);

}
