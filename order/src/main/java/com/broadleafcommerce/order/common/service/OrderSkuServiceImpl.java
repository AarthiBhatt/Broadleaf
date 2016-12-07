package com.broadleafcommerce.order.common.service;

import org.springframework.stereotype.Service;

import com.broadleafcommerce.order.common.dao.OrderSkuDao;
import com.broadleafcommerce.order.common.domain.OrderSku;

import javax.annotation.Resource;

/**
 * Created by brandon on 12/7/16.
 */
@Service("blOrderSkuService")
public class OrderSkuServiceImpl implements OrderSkuService {

    @Resource(name = "blOrderSkuDao")
    OrderSkuDao orderSkuDao;

    @Override
    public OrderSku saveOrderSku(OrderSku orderSku) {
        return orderSkuDao.save(orderSku);
    }

    @Override
    public OrderSku readOrderSkuById(Long orderSkuId) {
        return orderSkuDao.readOrderSkuById(orderSkuId);
    }

    @Override
    public OrderSku create() {
        return orderSkuDao.create();
    }

    @Override
    public void delete(OrderSku orderSku) {
        orderSkuDao.delete(orderSku);
    }
}
