package org.broadleafcommerce.core.order.service;

import org.broadleafcommerce.core.order.domain.OrderAddress;

/**
 * Created by brandon on 12/5/16.
 */
public interface OrderAddressService {

    public OrderAddress saveOrderAddress(OrderAddress orderAddress);

    public OrderAddress readOrderAddressById(Long orderAddressId);

    public OrderAddress create();

    public void delete(OrderAddress orderAddress);

    public OrderAddress copyOrderAddress(OrderAddress orig);

    public OrderAddress copyOrderAddress(OrderAddress dest, OrderAddress orig);

}
