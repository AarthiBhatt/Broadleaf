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
package org.broadleafcommerce.core.checkout.service.workflow;

import org.apache.commons.collections4.MapUtils;
import org.broadleafcommerce.core.inventory.service.ContextualInventoryService;
import org.broadleafcommerce.core.inventory.service.InventoryUnavailableException;
import org.broadleafcommerce.core.order.domain.DiscreteOrderItem;
import org.broadleafcommerce.core.order.domain.OrderItem;
import org.broadleafcommerce.core.workflow.BaseActivity;
import org.broadleafcommerce.core.workflow.ProcessContext;
import org.broadleafcommerce.core.workflow.state.ActivityStateManagerImpl;

import com.broadleafcommerce.order.common.domain.OrderSku;
import com.broadleafcommerce.order.common.domain.dto.OrderSkuDTO;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

/**
 * Decrements inventory
 * 
 * @author Phillip Verheyden (phillipuniverse)
 */
public class DecrementInventoryActivity extends BaseActivity<ProcessContext<CheckoutSeed>> {

    @Resource(name = "blInventoryService")
    protected ContextualInventoryService inventoryService;
    
    public DecrementInventoryActivity() {
        super();
        super.setAutomaticallyRegisterRollbackHandler(false);
    }

    @Override
    public ProcessContext<CheckoutSeed> execute(ProcessContext<CheckoutSeed> context) throws Exception {
        CheckoutSeed seed = context.getSeedData();
        
        Map<Long, Integer> skuQuantityMap = new HashMap<>();
        for (OrderItem orderItem : seed.getOrder().getOrderItems()) {
            if (orderItem instanceof DiscreteOrderItem) {
                OrderSku sku = ((DiscreteOrderItem) orderItem).getSku();
                Integer q = skuQuantityMap.get(sku);
                q = q == null ? orderItem.getQuantity() : q + orderItem.getQuantity();
                skuQuantityMap.put(sku.getExternalId(), q);
            }
        }


        Map<String, Object> rollbackState = new HashMap<String, Object>();
        if (getRollbackHandler() != null && !getAutomaticallyRegisterRollbackHandler()) {
            if (getStateConfiguration() != null && !getStateConfiguration().isEmpty()) {
                rollbackState.putAll(getStateConfiguration());
            }
            // Register the map with the rollback state object early on; this allows the extension handlers to incrementally
            // add state while decrementing but still throw an exception
            ActivityStateManagerImpl.getStateManager().registerState(this, context, getRollbackRegion(), getRollbackHandler(), rollbackState);
        }
            
        if (MapUtils.isNotEmpty(seed.getInventory())) {
            for (Entry<OrderSkuDTO, Integer> entry : seed.getInventory().entrySet()) {
                OrderSkuDTO dto = entry.getKey();
                Integer q = entry.getValue();
                if (q == null || q < 1) {
                    throw new IllegalArgumentException("Quantity " + q + " is not valid. Must be greater than zero and not null.");
                }
                if (!dto.isActive()) {
                    throw new IllegalArgumentException("The Sku " + dto.getSku().getExternalId() + "has been marked as unavailable");
                }
                Integer requestedQuantity = skuQuantityMap.get(dto.getSku().getExternalId());
                if (requestedQuantity > q) {
                    throw new InventoryUnavailableException(
                            "There was not enough inventory to fulfill this request because there is only " + q + " of sku " + dto.getSku().getExternalId() +
                                " but only " + requestedQuantity + " was available");

                }
            }
            // TODO microservices - implement event system to decrement quantity
            //inventoryService.decrementInventory(skuInventoryMap, contextualInfo);
            
            if (getRollbackHandler() != null && !getAutomaticallyRegisterRollbackHandler()) {
                rollbackState.put(DecrementInventoryRollbackHandler.ROLLBACK_BLC_INVENTORY_DECREMENTED, skuQuantityMap);
                rollbackState.put(DecrementInventoryRollbackHandler.ROLLBACK_BLC_ORDER_ID, seed.getOrder().getId());
            }
        }

        return context;
    }

}
