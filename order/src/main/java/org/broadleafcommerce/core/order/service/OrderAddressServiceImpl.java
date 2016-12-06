/*
 * #%L
 * BroadleafCommerce Order
 * %%
 * Copyright (C) 2009 - 2016 Broadleaf Commerce
 * %%
 * Licensed under the Broadleaf Fair Use License Agreement, Version 1.0
 * (the "Fair Use License" located  at http://license.broadleafcommerce.org/fair_use_license-1.0.txt)
 * unless the restrictions on use therein are violated and require payment to Broadleaf in which case
 * the Broadleaf End User License Agreement (EULA), Version 1.1
 * (the "Commercial License" located at http://license.broadleafcommerce.org/commercial_license-1.1.txt)
 * shall apply.
 * 
 * Alternatively, the Commercial License may be replaced with a mutually agreed upon license (the "Custom License")
 * between you and Broadleaf Commerce. You may not use this file except in compliance with the applicable license.
 * #L%
 */
package org.broadleafcommerce.core.order.service;

import org.broadleafcommerce.core.order.dao.OrderAddressDao;
import com.broadleafcommerce.order.common.domain.OrderAddress;
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

    @Override
    public OrderAddress copyOrderAddress(OrderAddress orig) {
        return copyOrderAddress(null, orig);
    }

    @Override
    public OrderAddress copyOrderAddress(OrderAddress dest, OrderAddress orig) {
        if (dest == null) {
            dest = create();
        }

        if (orig != null) {
            dest.setFullName(orig.getFullName());
            dest.setFirstName(orig.getFirstName());
            dest.setLastName(orig.getLastName());
            dest.setAddressLine1(orig.getAddressLine1());
            dest.setAddressLine2(orig.getAddressLine2());
            dest.setAddressLine3(orig.getAddressLine3());
            dest.setCityLocality(orig.getCityLocality());
            dest.setStateProvinceRegion(orig.getStateProvinceRegion());
            dest.setCountryCode(orig.getCountryCode());
            dest.setPostalCode(orig.getPostalCode());
            dest.setPhone(orig.getPhone());
            dest.setEmailAddress(orig.getEmailAddress());
            dest.setCompanyName(orig.getCompanyName());

            return dest;
        }

        return null;
    }
}
