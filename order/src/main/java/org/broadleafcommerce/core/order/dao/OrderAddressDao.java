package org.broadleafcommerce.core.order.dao;

import org.broadleafcommerce.core.order.domain.OrderAddress;

/**
 * Created by brandon on 12/5/16.
 */
public interface OrderAddressDao {

    public OrderAddress save(OrderAddress orderAddressddress);

    public OrderAddress readOrderAddressById(Long orderAddressId);

    public OrderAddress create();

    public void delete(OrderAddress address);

}
