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

import org.apache.commons.collections.CollectionUtils;
import org.broadleafcommerce.core.order.dao.OrderMultishipOptionDao;
import org.broadleafcommerce.core.order.domain.FulfillmentGroup;
import org.broadleafcommerce.core.order.domain.FulfillmentOption;
import org.broadleafcommerce.core.order.domain.Order;
import org.broadleafcommerce.core.order.domain.OrderItem;
import org.broadleafcommerce.core.order.domain.OrderMultishipOption;
import org.broadleafcommerce.core.order.domain.OrderMultishipOptionImpl;
import org.broadleafcommerce.core.order.service.call.OrderMultishipOptionDTO;
import org.broadleafcommerce.core.order.service.exception.ItemNotFoundException;
import org.springframework.stereotype.Service;

import com.broadleafcommerce.order.common.domain.OrderAddress;
import com.broadleafcommerce.order.common.service.OrderAddressService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

/**
 * 
 * @author Andre Azzolini (apazzolini)
 */
@Service("blOrderMultishipOptionService")
public class OrderMultishipOptionServiceImpl implements OrderMultishipOptionService {

    @Resource(name = "blOrderMultishipOptionDao")
    OrderMultishipOptionDao orderMultishipOptionDao;
    
    @Resource(name = "blOrderAddressService")
    protected OrderAddressService orderAddressService;
    
    @Resource(name = "blOrderItemService")
    protected OrderItemService orderItemService;
    
    @Resource(name = "blFulfillmentOptionService")
    protected FulfillmentOptionService fulfillmentOptionService;
    
    @Resource(name = "blFulfillmentGroupService")
    protected FulfillmentGroupService fulfillmentGroupService;

    @Override
    public OrderMultishipOption save(OrderMultishipOption orderMultishipOption) {
        return orderMultishipOptionDao.save(orderMultishipOption);
    }

    @Override
    public List<OrderMultishipOption> findOrderMultishipOptions(Long orderId) {
        return orderMultishipOptionDao.readOrderMultishipOptions(orderId);
    }
    
    @Override
    public List<OrderMultishipOption> findOrderItemOrderMultishipOptions(Long orderItemId) {
        return orderMultishipOptionDao.readOrderItemOrderMultishipOptions(orderItemId);
    }
    
    @Override
    public OrderMultishipOption create() {
        return orderMultishipOptionDao.create();
    }
    
    @Override
    public void deleteOrderItemOrderMultishipOptions(Long orderItemId) {
        List<OrderMultishipOption> options = findOrderItemOrderMultishipOptions(orderItemId);
        orderMultishipOptionDao.deleteAll(options);
    }
    
    @Override
    public void deleteOrderItemOrderMultishipOptions(Long orderItemId, int numToDelete) {
        List<OrderMultishipOption> options = findOrderItemOrderMultishipOptions(orderItemId);
        numToDelete = (numToDelete > options.size()) ? options.size() : numToDelete;
        options = options.subList(0, numToDelete);
        orderMultishipOptionDao.deleteAll(options);
    }
    
    @Override
    public void deleteAllOrderMultishipOptions(Order order) {
        List<OrderMultishipOption> options = findOrderMultishipOptions(order.getId());
        orderMultishipOptionDao.deleteAll(options);
    }
    
    @Override
    public void saveOrderMultishipOptions(Order order, List<OrderMultishipOptionDTO> optionDTOs) {
        Map<Long, OrderMultishipOption> currentOptions = new HashMap<Long, OrderMultishipOption>();
        for (OrderMultishipOption option : findOrderMultishipOptions(order.getId())) {
            currentOptions.put(option.getId(), option);
        }
        
        List<OrderMultishipOption> orderMultishipOptions = new ArrayList<OrderMultishipOption>();
        for (OrderMultishipOptionDTO dto: optionDTOs) {
            OrderMultishipOption option = currentOptions.get(dto.getId());
            if (option == null) {
                option = orderMultishipOptionDao.create();
            }
            
            option.setOrder(order);
            option.setOrderItem(orderItemService.readOrderItemById(dto.getOrderItemId()));
            
            if (dto.getAddressId() != null) {
                option.setAddress(orderAddressService.readOrderAddressById(dto.getAddressId()));
            } else {
                option.setAddress(null);
            }
            
            if (dto.getFulfillmentOptionId() != null) {
                option.setFulfillmentOption(fulfillmentOptionService.readFulfillmentOptionById(dto.getFulfillmentOptionId()));
            } else {
                option.setFulfillmentOption(null);
            }
            
            orderMultishipOptions.add(option);
        }
        
        for (OrderMultishipOption option : orderMultishipOptions) {
            save(option);
        }
    }
    
    @Override
    public List<OrderMultishipOption> getOrGenerateOrderMultishipOptions(Order order) {
        List<OrderMultishipOption> orderMultishipOptions = findOrderMultishipOptions(order.getId());
        if (orderMultishipOptions == null || orderMultishipOptions.size() == 0) {
            orderMultishipOptions = generateOrderMultishipOptions(order);
        }
        
        // Create a map representing the current discrete order item counts for the order
        Map<Long, Integer> orderDiscreteOrderItemCounts = new HashMap<Long, Integer>();
        for (OrderItem item : order.getOrderItems()) {
            orderDiscreteOrderItemCounts.put(item.getId(), item.getQuantity());
        }
        
        List<OrderMultishipOption> optionsToRemove = new ArrayList<OrderMultishipOption>();
        for (OrderMultishipOption option : orderMultishipOptions) {
            Integer count = orderDiscreteOrderItemCounts.get(option.getOrderItem().getId());
            if (count == null || count == 0) {
                optionsToRemove.add(option);
            } else {
                count--;
                orderDiscreteOrderItemCounts.put(option.getOrderItem().getId(), count);
            }
        }
        
        for (Entry<Long, Integer> entry : orderDiscreteOrderItemCounts.entrySet()) {
            OrderItem item = orderItemService.readOrderItemById(entry.getKey());
            orderMultishipOptions.addAll(createPopulatedOrderMultishipOption(order, item, entry.getValue()));
        }
        
        orderMultishipOptions.removeAll(optionsToRemove);
        orderMultishipOptionDao.deleteAll(optionsToRemove);
        
        return orderMultishipOptions;
    }
    
    @Override
    public List<OrderMultishipOption> getOrderMultishipOptionsFromDTOs(Order order, List<OrderMultishipOptionDTO> optionDtos) {
        List<OrderMultishipOption> orderMultishipOptions = new ArrayList<OrderMultishipOption>();
        for (OrderMultishipOptionDTO optionDto : optionDtos) {
            OrderMultishipOption option = new OrderMultishipOptionImpl();
            if (optionDto.getAddressId() != null) {
                option.setAddress(orderAddressService.readOrderAddressById(optionDto.getAddressId()));
            }   
            if (optionDto.getFulfillmentOptionId() != null) {
                option.setFulfillmentOption(fulfillmentOptionService.readFulfillmentOptionById(optionDto.getFulfillmentOptionId()));
            }
            option.setId(optionDto.getId());
            option.setOrder(order);
            option.setOrderItem(orderItemService.readOrderItemById(optionDto.getOrderItemId()));
            orderMultishipOptions.add(option);
        }
        return orderMultishipOptions;
    }
    
    @Override
    public List<OrderMultishipOption> generateOrderMultishipOptions(Order order) {
        List<OrderMultishipOption> orderMultishipOptions = new ArrayList<OrderMultishipOption>();
        for (OrderItem discreteOrderItem : order.getOrderItems()) {
            orderMultishipOptions.addAll(createPopulatedOrderMultishipOption(order, discreteOrderItem, discreteOrderItem.getQuantity()));
        }
        
        return orderMultishipOptions;
    }
    
    @Override
    public List<OrderMultishipOption> createMultishipOptionsToShipItemsToAddress(Order order, List<Long> orderItemIds, OrderAddress address, Long fulfillmentOptionId) throws ItemNotFoundException {
        List<OrderMultishipOption> options = findOrderMultishipOptions(order.getId());
        OrderAddress defaultAddress = null;
        FulfillmentOption defaultFulfillmentOption = null;
        Boolean shouldSaveMultishipOption = false;
        if (CollectionUtils.isEmpty(options)) {
            // There isn't any multiship options for an order so we need to get the address that was being
            // used on the shippable fulfillment group so that the fulfillment group items that aren't
            // explicitly sent can have the same address saved on them
            FulfillmentGroup fg = fulfillmentGroupService.getFirstShippableFulfillmentGroup(order);
            if (fg != null) {
                defaultAddress = fg.getAddress();
                defaultFulfillmentOption = fg.getFulfillmentOption();
            }
            options = generateOrderMultishipOptions(order);
            // Only save if new options were made
            shouldSaveMultishipOption = true;
        }
        Map<Long, List<OrderMultishipOption>> optionMap = new HashMap<>();
        for (OrderMultishipOption option : options) {
            if (defaultAddress != null) {
                option.setAddress(defaultAddress);
            }
            if (defaultFulfillmentOption != null) {
                option.setFulfillmentOption(defaultFulfillmentOption);
            }
            if (shouldSaveMultishipOption) {
                option = save(option);
            }
            List<OrderMultishipOption> itemOptions = optionMap.get(option.getOrderItem().getId());
            itemOptions = itemOptions == null ? new ArrayList<OrderMultishipOption>() : itemOptions;
            itemOptions.add(option);
            optionMap.put(option.getOrderItem().getId(), itemOptions);
        }
        FulfillmentOption newFulfillmentOption = defaultFulfillmentOption;
        if (fulfillmentOptionId != null) {
            newFulfillmentOption = fulfillmentOptionService.readFulfillmentOptionById(fulfillmentOptionId);
            if (newFulfillmentOption == null) {
                newFulfillmentOption = defaultFulfillmentOption;
            }
        }
        for (Long orderItemId : orderItemIds) {
            List<OrderMultishipOption> itemOptions = optionMap.get(orderItemId);
            if (itemOptions == null) {
                throw new ItemNotFoundException("Order item id " + orderItemId + " either doesn't exists on order with id " + order.getId() + " or it's no shippable");
            }
            for (OrderMultishipOption option : itemOptions) {
                option.setFulfillmentOption(newFulfillmentOption);
                option.setAddress(address);
                option = save(option);
            }
        }
        return options;
    }
    
    protected List<OrderMultishipOption> createPopulatedOrderMultishipOption(Order order, OrderItem item, Integer quantity) {
        List<OrderMultishipOption> orderMultishipOptions = new ArrayList<OrderMultishipOption>();
        if (item.getOrderItemDetail() == null || !fulfillmentGroupService.isShippable(item.getOrderItemDetail().getFulfillmentType())) {
            return orderMultishipOptions;
        }
        for (int i = 0; i < quantity; i++) {
            OrderMultishipOption orderMultishipOption = new OrderMultishipOptionImpl();
            orderMultishipOption.setOrder(order);
            orderMultishipOption.setOrderItem(item);
            orderMultishipOptions.add(orderMultishipOption);
        }
        return orderMultishipOptions;
    }
}
