package org.broadleafcommerce.core.order.service;

import org.broadleafcommerce.core.order.dao.OrderAddressDao;
import org.broadleafcommerce.core.order.domain.OrderAddress;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

/**
 * Created by brandon on 12/5/16.
 */
@Service("blOrderAddressService")
public class OrderAddressServiceImpl implements OrderAddressService {

    @Resource(name = "blOrderAddressDao")
    protected OrderAddressDao orderAddressDao;

    @Override
    public OrderAddress saveOrderAddress(OrderAddress orderAddress) { return orderAddressDao.save(orderAddress); }

    @Override
    public OrderAddress readOrderAddressById(Long orderAddressId) { return orderAddressDao.readOrderAddressById(orderAddressId); }

    @Override
    public OrderAddress create() { return orderAddressDao.create(); }

    @Override
    public void delete(OrderAddress orderAddress) { orderAddressDao.delete(orderAddress); }

}
