package com.broadleafcommerce.order.common.dto;

import org.broadleafcommerce.common.money.Money;
import org.broadleafcommerce.core.order.domain.OrderItem;

import java.io.Serializable;

import lombok.Data;
import lombok.NonNull;

@Data
public class OrderItemDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    
    public Long id;
    public int quantity;
    public Money retailPrice;
    public Money salePrice;
    public String name;
    public Boolean itemTaxable;
    public Boolean retailPriceOverride;
    public Boolean salePriceOverride;
    
    public OrderItemDTO(@NonNull OrderItem item) {
        this.id = item.getId();
        this.quantity = item.getQuantity();
        this.retailPrice = item.getRetailPrice();
        this.salePrice = item.getSalePrice();
        this.name = item.getName();
        this.itemTaxable = item.getIsDiscounted();
        this.retailPriceOverride = item.isRetailPriceOverride();
        this.salePriceOverride = item.isSalePriceOverride();
    }
}
