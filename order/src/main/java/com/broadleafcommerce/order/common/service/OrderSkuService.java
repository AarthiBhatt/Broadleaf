package com.broadleafcommerce.order.common.service;

import com.broadleafcommerce.order.common.domain.OrderSku;

/**
 * Created by brandon on 12/7/16.
 */
public interface OrderSkuService {

    public OrderSku saveOrderSku(OrderSku orderSku);

    public OrderSku readOrderSkuById(Long orderSkuId);

    public OrderSku create();

    public void delete(OrderSku orderSku);

}
