package com.broadleafcommerce.order.common.domain.dto;

import org.broadleafcommerce.common.money.Money;

import com.broadleafcommerce.order.common.domain.OrderSku;


public class OrderSkuDTO {

    protected String name;
    protected OrderSku sku;
    protected Money retailPrice;
    protected Money salePrice;
    protected boolean taxable;
    protected boolean discountable;
    protected boolean active;
    
    public OrderSku getSku() {
        return sku;
    }
    
    public void setSku(OrderSku sku) {
        this.sku = sku;
    }
    
    public Money getRetailPrice() {
        return retailPrice;
    }
    
    public void setRetailPrice(Money retailPrice) {
        this.retailPrice = retailPrice;
    }
    
    public Money getSalePrice() {
        return salePrice;
    }
    
    public void setSalePrice(Money salePrice) {
        this.salePrice = salePrice;
    }
    
    public boolean isTaxable() {
        return taxable;
    }
    
    public void setTaxable(boolean taxable) {
        this.taxable = taxable;
    }
    
    public boolean hasRetailPrice() {
        return getRetailPrice() != null;
    }
    
    public boolean hasSalePrice() {
        return getSalePrice() != null;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public boolean isDiscountable() {
        return discountable;
    }
    
    public void setDiscountable(boolean discountable) {
        this.discountable = discountable;
    }
    
    public boolean isActive() {
        return active;
    }
    
    public void setActive(boolean active) {
        this.active = active;
    }
}
