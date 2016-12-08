/*
 * #%L
 * BroadleafCommerce Framework
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

import org.broadleafcommerce.common.util.DimensionUnitOfMeasureType;
import org.broadleafcommerce.common.util.WeightUnitOfMeasureType;
import org.broadleafcommerce.core.order.dao.OrderItemDao;
import org.broadleafcommerce.core.order.domain.GiftWrapOrderItem;
import org.broadleafcommerce.core.order.domain.Order;
import org.broadleafcommerce.core.order.domain.OrderItem;
import org.broadleafcommerce.core.order.domain.OrderItemAttribute;
import org.broadleafcommerce.core.order.domain.OrderItemAttributeImpl;
import org.broadleafcommerce.core.order.domain.PersonalMessage;
import org.broadleafcommerce.core.order.service.call.ConfigurableOrderItemRequest;
import org.broadleafcommerce.core.order.service.call.GiftWrapOrderItemRequest;
import org.broadleafcommerce.core.order.service.call.OrderItemRequest;
import org.broadleafcommerce.core.order.service.call.OrderItemRequestDTO;
import org.broadleafcommerce.core.order.service.extension.OrderItemServiceExtensionManager;
import org.broadleafcommerce.core.order.service.type.FulfillmentType;
import org.broadleafcommerce.core.order.service.type.OrderItemType;
import org.springframework.stereotype.Service;

import com.broadleafcommerce.order.common.domain.OrderSku;
import com.broadleafcommerce.order.common.dto.OrderSkuDTO;
import com.broadleafcommerce.order.common.service.OrderSkuService;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Resource;

@Service("blOrderItemService")
public class OrderItemServiceImpl implements OrderItemService {

    @Resource(name="blOrderItemDao")
    protected OrderItemDao orderItemDao;
    
    @Resource(name="blOrderItemServiceExtensionManager")
    protected OrderItemServiceExtensionManager extensionManager;

    @Resource(name="blOrderSkuService")
    protected OrderSkuService orderSkuService;

    @Override
    public OrderItem readOrderItemById(final Long orderItemId) {
        return orderItemDao.readOrderItemById(orderItemId);
    }

    @Override
    public OrderItem saveOrderItem(final OrderItem orderItem) {
        return orderItemDao.saveOrderItem(orderItem);
    }
    
    @Override
    public void delete(final OrderItem item) {
        orderItemDao.delete(item);
    }
    
    @Override
    public PersonalMessage createPersonalMessage() {
        return orderItemDao.createPersonalMessage();
    }

    @Override
    public void populateProductOptionAttributes(OrderItem item, Map<String, String> attributes) {
        if (attributes != null && attributes.size() > 0) {
            Map<String, OrderItemAttribute> orderItemAttributes = item.getOrderItemAttributes();
            if (item.getOrderItemAttributes() == null) {
                orderItemAttributes = new HashMap<String, OrderItemAttribute>();
                item.setOrderItemAttributes(orderItemAttributes);
            }
            for (String key : attributes.keySet()) {
                String value = attributes.get(key);
                OrderItemAttribute attribute = new OrderItemAttributeImpl();
                attribute.setName(key);
                attribute.setValue(value);
                attribute.setOrderItem(item);
                orderItemAttributes.put(key, attribute);
            }
        }
    }
    
    @Override
    public OrderItem createOrderItem(final OrderItemRequest itemRequest) {
        final OrderItem item = orderItemDao.create(OrderItemType.BASIC);
        item.setName(itemRequest.getSku().getName());
        item.setQuantity(itemRequest.getQuantity());
        item.setOrder(itemRequest.getOrder());
        item.setSku(itemRequest.getSku());

        item.setSalePrice(itemRequest.getSku().getSalePrice());
        item.setRetailPrice(itemRequest.getSku().getRetailPrice());

        if (itemRequest.getSalePriceOverride() != null) {
            item.setSalePriceOverride(Boolean.TRUE);
            item.setSalePrice(itemRequest.getSalePriceOverride());
        }

        if (itemRequest.getRetailPriceOverride() != null) {
            item.setRetailPriceOverride(Boolean.TRUE);
            item.setRetailPrice(itemRequest.getRetailPriceOverride());
        }

        Map<String, String> attributes = itemRequest.getItemAttributes();
        populateProductOptionAttributes(item, attributes);

        item.setPersonalMessage(itemRequest.getPersonalMessage());
        applyAdditionalOrderItemProperties(item);

        return item;
    }

    @Override
    public OrderItem updateOrderItem(OrderItem item, final OrderItemRequest itemRequest) {
        item.setSku(itemRequest.getSku());
        populateProductOptionAttributes(item, itemRequest.getItemAttributes());
        applyAdditionalOrderItemProperties(item);
        return item;
    }

//TODO: microservices - deal with dynamic pricing in catalog before sending item requests
//    @Override
//    public DiscreteOrderItem createDynamicPriceDiscreteOrderItem(final DiscreteOrderItemRequest itemRequest, @SuppressWarnings("rawtypes") HashMap skuPricingConsiderations) {
//        final DiscreteOrderItem item = (DiscreteOrderItem) orderItemDao.create(OrderItemType.EXTERNALLY_PRICED);
//        populateDiscreteOrderItem(item, itemRequest);
//
//        SkuPriceWrapper wrapper = new SkuPriceWrapper(itemRequest.getSku());
//        DynamicSkuPrices prices = dynamicSkuPricingService.getSkuPrices(wrapper, skuPricingConsiderations);
//        item.setBaseRetailPrice(prices.getRetailPrice());
//        item.setBaseSalePrice(prices.getSalePrice());
//        item.setSalePrice(prices.getSalePrice());
//        item.setRetailPrice(prices.getRetailPrice());
//
//        if (itemRequest.getSalePriceOverride() != null) {
//            item.setSalePriceOverride(Boolean.TRUE);
//            item.setSalePrice(itemRequest.getSalePriceOverride());
//            item.setBaseSalePrice(itemRequest.getSalePriceOverride());
//        }
//
//        if (itemRequest.getRetailPriceOverride() != null) {
//            item.setRetailPriceOverride(Boolean.TRUE);
//            item.setRetailPrice(itemRequest.getRetailPriceOverride());
//            item.setBaseRetailPrice(itemRequest.getRetailPriceOverride());
//        }
//
//        item.setDiscreteOrderItemFeePrices(itemRequest.getDiscreteOrderItemFeePrices());
//        for (DiscreteOrderItemFeePrice fee : itemRequest.getDiscreteOrderItemFeePrices()) {
//            item.setSalePrice(item.getSalePrice().add(fee.getAmount()));
//            item.setRetailPrice(item.getRetailPrice().add(fee.getAmount()));
//        }
//
//        item.setPersonalMessage(itemRequest.getPersonalMessage());
//
//        applyAdditionalOrderItemProperties(item);
//
//        return item;
//    }

    @Override
    public GiftWrapOrderItem createGiftWrapOrderItem(final GiftWrapOrderItemRequest itemRequest) {
        final GiftWrapOrderItem item = (GiftWrapOrderItem) orderItemDao.create(OrderItemType.GIFTWRAP);
        item.setSku(itemRequest.getSku());
        item.setOrder(itemRequest.getOrder());
        item.setQuantity(itemRequest.getQuantity());
        item.setBaseSalePrice(itemRequest.getSku().getSalePrice());
        item.setBaseRetailPrice(itemRequest.getSku().getRetailPrice());

        if (itemRequest.getSalePriceOverride() != null) {
            item.setSalePriceOverride(Boolean.TRUE);
            item.setSalePrice(itemRequest.getSalePriceOverride());
            item.setBaseSalePrice(itemRequest.getSalePriceOverride());
        }

        if (itemRequest.getRetailPriceOverride() != null) {
            item.setRetailPriceOverride(Boolean.TRUE);
            item.setRetailPrice(itemRequest.getRetailPriceOverride());
            item.setBaseRetailPrice(itemRequest.getRetailPriceOverride());
        }

        //item.updatePrices();
        item.updateSaleAndRetailPrices();
        item.assignFinalPrice();
        item.getWrappedItems().addAll(itemRequest.getWrappedItems());
        for (OrderItem orderItem : item.getWrappedItems()) {
            orderItem.setGiftWrapOrderItem(item);
        }

        applyAdditionalOrderItemProperties(item);

        return item;
    }

    @Override
    public OrderItemRequestDTO buildOrderItemRequestDTOFromOrderItem(OrderItem item) {
        OrderItemRequestDTO orderItemRequest = new OrderItemRequestDTO();

        orderItemRequest.setOrderSkuDTO(buildOrderSkuDTOFromSku(item.getSku()));
        orderItemRequest.setQuantity(item.getQuantity());
        if (item.getOrderItemAttributes() != null) {
            for (Entry<String, OrderItemAttribute> entry : item.getOrderItemAttributes().entrySet()) {
                orderItemRequest.getItemAttributes().put(entry.getKey(), entry.getValue().getValue());
            }
        }

        orderItemRequest.setQuantity(item.getQuantity());
        orderItemRequest.setOverrideRetailPrice(item.getRetailPrice());
        orderItemRequest.setOverrideSalePrice(item.getSalePrice());

        return orderItemRequest;
    }

    protected OrderSkuDTO buildOrderSkuDTOFromSku(OrderSku orderSku) {
        OrderSkuDTO orderSkuDTO = new OrderSkuDTO();

        orderSkuDTO.setExternalId(orderSku.getExternalId());
        orderSkuDTO.setName(orderSku.getName());
        orderSkuDTO.setFulfillmentType(orderSku.getFulfillmentType().getType());

        orderSkuDTO.setTaxable(orderSku.getTaxable());
        orderSkuDTO.setTaxCode(orderSku.getTaxCode());
        orderSkuDTO.setRetailPrice(orderSku.getRetailPrice());
        orderSkuDTO.setSalePrice(orderSku.getSalePrice());

        //dimensions
        orderSkuDTO.setContainer(orderSku.getContainer());
        orderSkuDTO.setSize(orderSku.getSize());
        orderSkuDTO.setWidth(orderSku.getWidth());
        orderSkuDTO.setHeight(orderSku.getHeight());
        orderSkuDTO.setDepth(orderSku.getDepth());
        orderSkuDTO.setGirth(orderSku.getGirth());
        if (orderSku.getDimensionUnitOfMeasure() != null) {
            orderSkuDTO.setDimensionUnitOfMeasure(orderSku.getDimensionUnitOfMeasure().getType());
        }

        //weight
        orderSkuDTO.setWeight(orderSku.getWeight());
        if (orderSku.getWeightUnitOfMeasure() != null) {
            orderSkuDTO.setWeightUnitOfMeasure(orderSku.getWeightUnitOfMeasure().getType());
        }

        return orderSkuDTO;
    }

    @Override
    public OrderItem buildOrderItemFromDTO(Order order, OrderItemRequestDTO orderItemRequestDTO) {
        OrderSku orderSku = buildOrderSkuFromDTO(orderItemRequestDTO.getOrderSkuDTO());

        OrderItemRequest itemRequest = new OrderItemRequest();
        itemRequest.setSku(orderSku);
        itemRequest.setQuantity(orderItemRequestDTO.getQuantity());
        itemRequest.setItemAttributes(orderItemRequestDTO.getItemAttributes());
        itemRequest.setOrder(order);
        itemRequest.setSalePriceOverride(orderItemRequestDTO.getOverrideSalePrice());
        itemRequest.setRetailPriceOverride(orderItemRequestDTO.getOverrideRetailPrice());

        OrderItem item = createOrderItem(itemRequest);

        if (orderItemRequestDTO.getParentOrderItemId() != null) {
            OrderItem parent = readOrderItemById(orderItemRequestDTO.getParentOrderItemId());
            item.setParentOrderItem(parent);
        }

        if (orderItemRequestDTO.getHasConfigurationError()) {
            item.setHasValidationError(true);
        }

        if (orderItemRequestDTO instanceof ConfigurableOrderItemRequest) {
            ConfigurableOrderItemRequest configRequest = (ConfigurableOrderItemRequest) orderItemRequestDTO;
            if (configRequest.getHasConfigurationError()) {
                item.setHasValidationError(true);
            }
            if (!configRequest.getDiscountsAllowed()) {
                item.setDiscountingAllowed(false);
            }
        }

        applyAdditionalOrderItemProperties(item);

        return item;
    }

    protected OrderSku buildOrderSkuFromDTO(OrderSkuDTO orderSkuDTO) {
        OrderSku orderSku = orderSkuService.create();
        orderSku.setExternalId(orderSkuDTO.getExternalId());
        orderSku.setName(orderSkuDTO.getName());

        if (FulfillmentType.getInstance(orderSkuDTO.getFulfillmentType()) != null) {
            orderSku.setFulfillmentType(FulfillmentType.getInstance(orderSkuDTO.getFulfillmentType()));
        }

        orderSku.setTaxable(orderSkuDTO.getTaxable());
        orderSku.setTaxCode(orderSkuDTO.getTaxCode());
        orderSku.setRetailPrice(orderSkuDTO.getRetailPrice());
        orderSku.setSalePrice(orderSkuDTO.getSalePrice());

        //dimensions
        orderSku.setContainer(orderSkuDTO.getContainer());
        orderSku.setSize(orderSkuDTO.getSize());
        orderSku.setWidth(orderSkuDTO.getWidth());
        orderSku.setHeight(orderSkuDTO.getHeight());
        orderSku.setDepth(orderSkuDTO.getDepth());
        orderSku.setGirth(orderSkuDTO.getGirth());
        if (DimensionUnitOfMeasureType.getInstance(orderSkuDTO.getDimensionUnitOfMeasure()) != null) {
            orderSku.setDimensionUnitOfMeasure(DimensionUnitOfMeasureType.getInstance(orderSkuDTO.getDimensionUnitOfMeasure()));
        }

        //weight
        orderSku.setWeight(orderSkuDTO.getWeight());
        if (WeightUnitOfMeasureType.getInstance(orderSkuDTO.getWeightUnitOfMeasure()) != null) {
            orderSku.setWeightUnitOfMeasure(WeightUnitOfMeasureType.getInstance(orderSkuDTO.getWeightUnitOfMeasure()));
        }

        return orderSku;
    }

    @Override
    public void priceOrderItem(OrderItem item) {
        extensionManager.getProxy().modifyOrderItemPrices(item);
    }

//TODO: microservices - deal with finding all the sku's from a configurable request in the catalog service
//    @Override
//    public Set<Product> findAllProductsInRequest(ConfigurableOrderItemRequest itemRequest) {
//        Set<Product> allProductsSet = findAllChildProductsInRequest(itemRequest.getChildOrderItems());
//        allProductsSet.add(itemRequest.getProduct());
//        return allProductsSet;
//    }
//
//    protected Set<Product> findAllChildProductsInRequest(List<OrderItemRequestDTO> childItems) {
//        Set<Product> allProductsSet = new HashSet<Product>();
//        for (OrderItemRequestDTO child : childItems) {
//            ConfigurableOrderItemRequest configChild = (ConfigurableOrderItemRequest) child;
//            Product childProduct = configChild.getProduct();
//            if (childProduct != null) {
//                allProductsSet.add(childProduct);
//            } else {
//                List<OrderItemRequestDTO> productChoices = new ArrayList<OrderItemRequestDTO>(configChild.getProductChoices());
//                allProductsSet.addAll(findAllChildProductsInRequest(productChoices));
//            }
//        }
//        return allProductsSet;
//    }

    @Override
    public void applyAdditionalOrderItemProperties(OrderItem orderItem) {
        extensionManager.getProxy().applyAdditionalOrderItemProperties(orderItem);
    }

//TODO: microservices - deal with creating configurable request from product
//    @Override
//    public ConfigurableOrderItemRequest createConfigurableOrderItemRequestFromProduct(Product product) {
//        ConfigurableOrderItemRequest itemRequest = new ConfigurableOrderItemRequest();
//        itemRequest.setSkuId(product.getDefaultSku().getId());
//        itemRequest.setQuantity(1);
//        itemRequest.setDisplayPrice(product.getDefaultSku().getPrice());
//        itemRequest.setDiscountsAllowed(product.getDefaultSku().isDiscountable());
//        return itemRequest;
//    }

    @Override
    public void modifyOrderItemRequest(ConfigurableOrderItemRequest itemRequest) {
        extensionManager.getProxy().modifyOrderItemRequest(itemRequest);
    }

    @Override
    public void mergeOrderItemRequest(ConfigurableOrderItemRequest itemRequest, OrderItem orderItem) {
        extensionManager.getProxy().mergeOrderItemRequest(itemRequest, orderItem);
    }

    public List<OrderItem> findOrderItemsForCustomersInDateRange(List<Long> customerIds, Date startDate, Date endDate) {
        return orderItemDao.readOrderItemsForCustomersInDateRange(customerIds, startDate, endDate);
    }
}
