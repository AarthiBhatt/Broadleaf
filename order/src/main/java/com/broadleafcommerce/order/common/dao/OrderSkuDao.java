package com.broadleafcommerce.order.common.dao;

import com.broadleafcommerce.order.common.domain.OrderSku;

/**
 * Created by brandon on 12/7/16.
 */
public interface OrderSkuDao {
    public OrderSku save(OrderSku orderSku);

    public OrderSku readOrderSkuById(Long orderSkuId);

    public OrderSku create();

    public void delete(OrderSku orderSku);
}
