package com.broadleafcommerce.order.common.domain;


public class OrderCategoryImpl implements OrderCategory {

    protected Long externalId;
    
    @Override
    public Long getExternalId() {
        return externalId;
    }
}
