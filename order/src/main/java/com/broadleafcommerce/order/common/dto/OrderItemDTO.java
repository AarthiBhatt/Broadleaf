package com.broadleafcommerce.order.common.dto;

import org.broadleafcommerce.common.api.APIWrapper;
import org.broadleafcommerce.common.api.BaseWrapper;
import org.broadleafcommerce.common.money.Money;
import org.broadleafcommerce.core.order.domain.OrderItem;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.servlet.http.HttpServletRequest;

import lombok.Data;

@Data
public class OrderItemDTO extends BaseWrapper implements APIWrapper<OrderItem> {

    private static final long serialVersionUID = 1L;
    
    @JsonProperty("id")
    protected Long id;
    
    @JsonProperty("quantity")
    protected int quantity;
    
    @JsonProperty("retailPrice")
    protected Money retailPrice;
    
    @JsonProperty("salePrice")
    protected Money salePrice;
    
    @JsonProperty("name")
    protected String name;
    
    @JsonProperty("itemTaxable")
    protected Boolean itemTaxable;
    
    @JsonProperty("hasRetailPriceOverride")
    protected Boolean retailPriceOverride;
    
    @JsonProperty("hasSalePriceOverride")
    protected Boolean salePriceOverride;

    @Override
    public void wrapDetails(OrderItem item, HttpServletRequest request) {
        this.id = item.getId();
        this.quantity = item.getQuantity();
        this.retailPrice = item.getRetailPrice();
        this.salePrice = item.getSalePrice();
        this.name = item.getName();
        this.itemTaxable = item.getIsDiscounted();
        this.retailPriceOverride = item.isRetailPriceOverride();
        this.salePriceOverride = item.isSalePriceOverride();
    }

    @Override
    public void wrapSummary(OrderItem item, HttpServletRequest request) {
        wrapDetails(item, request);
    }
}
