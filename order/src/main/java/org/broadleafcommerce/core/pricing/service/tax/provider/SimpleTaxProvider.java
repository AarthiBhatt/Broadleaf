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
package org.broadleafcommerce.core.pricing.service.tax.provider;

import org.apache.commons.lang.StringUtils;
import org.broadleafcommerce.common.config.domain.ModuleConfiguration;
import org.broadleafcommerce.common.persistence.EntityConfiguration;
import org.broadleafcommerce.core.order.domain.FulfillmentGroup;
import org.broadleafcommerce.core.order.domain.FulfillmentGroupFee;
import org.broadleafcommerce.core.order.domain.FulfillmentGroupItem;
import org.broadleafcommerce.core.order.domain.Order;
import org.broadleafcommerce.core.order.domain.TaxDetail;
import org.broadleafcommerce.core.order.domain.TaxType;
import org.broadleafcommerce.core.pricing.service.exception.TaxException;

import com.broadleafcommerce.order.common.domain.OrderAddress;

import java.math.BigDecimal;
import java.util.Map;

import javax.annotation.Resource;

/**
 * <p>
 * Simple factor-based tax module that can be configured by adding rates for
 * specific postalCodes, city, state, or country.
 *
 * <p>
 * Through configuration, this module can be used to set a specific tax rate for items and shipping for a given postal code,
 * city, state, or country.
 * 
 * <p>
 * Utilizes the fulfillment group's address to determine the tax location.
 * 
 * <p>
 * Useful for those with very simple tax needs that want to configure rates programmatically.
 * 
 * @author jfischer, brian polster
 * @author Phillip Verheyden (phillipuniverse)
 */
public class SimpleTaxProvider implements TaxProvider {

    protected Map<String, Double> itemPostalCodeTaxRateMap;
    protected Map<String, Double> itemCityTaxRateMap;
    protected Map<String, Double> itemStateTaxRateMap;
    protected Map<String, Double> itemCountryTaxRateMap;

    protected Map<String, Double> fulfillmentGroupPostalCodeTaxRateMap;
    protected Map<String, Double> fulfillmentGroupCityTaxRateMap;
    protected Map<String, Double> fulfillmentGroupStateTaxRateMap;
    protected Map<String, Double> fulfillmentGroupCountryTaxRateMap;

    protected Double defaultItemTaxRate;
    protected Double defaultFulfillmentGroupTaxRate;

    protected boolean taxFees;
    
    @Resource(name = "blEntityConfiguration")
    protected EntityConfiguration entityConfig;
    
    @Override
    public boolean canRespond(ModuleConfiguration config) {
        // this will only be executed with null module configurations
        return config == null;
    }

    @Override
    public Order calculateTaxForOrder(Order order, ModuleConfiguration config) throws TaxException {
        if (!order.isTaxExempt()) {
            for (FulfillmentGroup fulfillmentGroup : order.getFulfillmentGroups()) {
                // Set taxes on the fulfillment group items
                for (FulfillmentGroupItem fgItem : fulfillmentGroup.getFulfillmentGroupItems()) {
                    if (isItemTaxable(fgItem)) {
                        BigDecimal factor = determineItemTaxRate(fulfillmentGroup.getAddress());
                        if (factor != null && factor.compareTo(BigDecimal.ZERO) != 0) {
                            TaxDetail tax;
                            checkDetail: {
                                for (TaxDetail detail : fgItem.getTaxes()) {
                                    if (detail.getType().equals(TaxType.COMBINED)) {
                                        tax = detail;
                                        break checkDetail;
                                    }
                                }
                                tax = entityConfig.createEntityInstance(TaxDetail.class.getName(), TaxDetail.class);
                                tax.setType(TaxType.COMBINED);
                                fgItem.getTaxes().add(tax);
                            }
                            tax.setRate(factor);
                            tax.setAmount(fgItem.getTotalItemTaxableAmount().multiply(factor));
                        }
                    }
                }
    
                for (FulfillmentGroupFee fgFee : fulfillmentGroup.getFulfillmentGroupFees()) {
                    if (isFeeTaxable(fgFee)) {
                        BigDecimal factor = determineItemTaxRate(fulfillmentGroup.getAddress());
                        if (factor != null && factor.compareTo(BigDecimal.ZERO) != 0) {
                            TaxDetail tax;
                            checkDetail: {
                                for (TaxDetail detail : fgFee.getTaxes()) {
                                    if (detail.getType().equals(TaxType.COMBINED)) {
                                        tax = detail;
                                        break checkDetail;
                                    }
                                }
                                tax = entityConfig.createEntityInstance(TaxDetail.class.getName(), TaxDetail.class);
                                tax.setType(TaxType.COMBINED);
                                fgFee.getTaxes().add(tax);
                            }
                            tax.setRate(factor);
                            tax.setAmount(fgFee.getAmount().multiply(factor));
                        }
                    }
                }
    
                BigDecimal factor = determineTaxRateForFulfillmentGroup(fulfillmentGroup);
                if (factor != null && factor.compareTo(BigDecimal.ZERO) != 0) {
                    TaxDetail tax;
                    checkDetail: {
                        for (TaxDetail detail : fulfillmentGroup.getTaxes()) {
                            if (detail.getType().equals(TaxType.COMBINED)) {
                                tax = detail;
                                break checkDetail;
                            }
                        }
                        tax = entityConfig.createEntityInstance(TaxDetail.class.getName(), TaxDetail.class);
                        tax.setType(TaxType.COMBINED);
                        fulfillmentGroup.getTaxes().add(tax);
                    }
                    tax.setRate(factor);
                    tax.setAmount(fulfillmentGroup.getFulfillmentPrice().multiply(factor));
                }
            }
        }

        return order;
    }

    @Override
    public Order commitTaxForOrder(Order order, ModuleConfiguration config) throws TaxException {
        // intentionally left blank; no tax needs to be committed as this already has the tax details on the order
        return order;
    }

    @Override
    public void cancelTax(Order order, ModuleConfiguration config) throws TaxException {
        // intentionally left blank; tax never got committed so it never gets cancelled
    }

    /**
     * Returns the taxAmount for the passed in postal code or
     * null if no match is found.
     *
     * @param postalCode
     * @return
     */
    public Double lookupPostalCodeRate(Map<String,Double> postalCodeTaxRateMap, String postalCode) {
        if (postalCodeTaxRateMap != null && postalCode != null) {
            return postalCodeTaxRateMap.get(postalCode);
        }
        return null;
    }

    /**
     * Returns the taxAmount for the passed in area or
     * null if no match is found.
     * 
     * @param stateTaxRateMap, area
     * @return
     */
    public Double lookupRate(Map<String,Double> taxRateMap, String area) {
        if (taxRateMap != null && StringUtils.isNotBlank(area)) {
            return taxRateMap.get(area.toUpperCase());
        }
        return null;
    }

    protected boolean isItemTaxable(FulfillmentGroupItem item) {
        return item.getOrderItem().isTaxable();
    }

    protected boolean isFeeTaxable(FulfillmentGroupFee fee) {
        return fee.isTaxable();
    }


    /**
     * Uses the passed in address to determine if the item is taxable.
     *
     * Checks the configured maps in order - (postal code, city, state, country)
     *
     * @param address
     * @return
     */
    public BigDecimal determineItemTaxRate(OrderAddress address) {
        if (address != null) {
            Double postalCodeRate = lookupPostalCodeRate(itemPostalCodeTaxRateMap, address.getPostalCode());
            if (postalCodeRate != null) {
                return BigDecimal.valueOf(postalCodeRate);
            }
            Double cityCodeRate = lookupRate(itemCityTaxRateMap, address.getCityLocality());
            if (cityCodeRate != null) {
                return BigDecimal.valueOf(cityCodeRate);
            }

            Double stateCodeRate;
            if (StringUtils.isNotBlank(address.getStateProvinceRegion())) {
                stateCodeRate = lookupRate(itemStateTaxRateMap, address.getStateProvinceRegion());
            } else {
                stateCodeRate = lookupRate(itemStateTaxRateMap, address.getStateProvinceRegion());
            }

            if (stateCodeRate != null) {
                return BigDecimal.valueOf(stateCodeRate);
            }

            Double countryCodeRate = lookupRate(itemCountryTaxRateMap, address.getCountryCode());

            if (countryCodeRate != null) {
                return BigDecimal.valueOf(countryCodeRate);
            }
        }

        if (defaultItemTaxRate != null) {
            return BigDecimal.valueOf(defaultItemTaxRate);
        } else {
            return BigDecimal.ZERO;
        }
    }

    /**
     * Uses the passed in address to determine if the item is taxable.
     *
     * Checks the configured maps in order - (postal code, city, state, country)
     *
     * @param fulfillmentGroup
     * @return
     */
    public BigDecimal determineTaxRateForFulfillmentGroup(FulfillmentGroup fulfillmentGroup) {
        boolean isTaxable = true;

        if (fulfillmentGroup.isShippingPriceTaxable() != null) {
            isTaxable = fulfillmentGroup.isShippingPriceTaxable();
        }

        if (isTaxable) {
            OrderAddress address = fulfillmentGroup.getAddress();
            if (address != null) {
                Double postalCodeRate = lookupPostalCodeRate(fulfillmentGroupPostalCodeTaxRateMap, address.getPostalCode());
                if (postalCodeRate != null) {
                    return BigDecimal.valueOf(postalCodeRate);
                }
                Double cityCodeRate = lookupRate(fulfillmentGroupCityTaxRateMap, address.getCityLocality());
                if (cityCodeRate != null) {
                    return BigDecimal.valueOf(cityCodeRate);
                }

                Double stateCodeRate;
                if (StringUtils.isNotBlank(address.getStateProvinceRegion())) {
                    stateCodeRate = lookupRate(fulfillmentGroupStateTaxRateMap, address.getStateProvinceRegion());
                } else {
                    stateCodeRate = lookupRate(fulfillmentGroupStateTaxRateMap, address.getStateProvinceRegion());
                }

                if (stateCodeRate != null) {
                    return BigDecimal.valueOf(stateCodeRate);
                }

                Double countryCodeRate = lookupRate(fulfillmentGroupCountryTaxRateMap, address.getCountryCode());

                if (countryCodeRate != null) {
                    return BigDecimal.valueOf(countryCodeRate);
                }
            }

            if (defaultFulfillmentGroupTaxRate != null) {
                return BigDecimal.valueOf(defaultFulfillmentGroupTaxRate);
            }
        }
        return BigDecimal.ZERO;
    }

    public Map<String, Double> getItemPostalCodeTaxRateMap() {
        return itemPostalCodeTaxRateMap;
    }

    public void setItemPostalCodeTaxRateMap(Map<String, Double> itemPostalCodeTaxRateMap) {
        this.itemPostalCodeTaxRateMap = itemPostalCodeTaxRateMap;
    }

    public Map<String, Double> getItemCityTaxRateMap() {
        return itemCityTaxRateMap;
    }

    public void setItemCityTaxRateMap(Map<String, Double> itemCityTaxRateMap) {
        this.itemCityTaxRateMap = itemCityTaxRateMap;
    }

    public Map<String, Double> getItemStateTaxRateMap() {
        return itemStateTaxRateMap;
    }

    public void setItemStateTaxRateMap(Map<String, Double> itemStateTaxRateMap) {
        this.itemStateTaxRateMap = itemStateTaxRateMap;
    }

    public Map<String, Double> getItemCountryTaxRateMap() {
        return itemCountryTaxRateMap;
    }

    public void setItemCountryTaxRateMap(Map<String, Double> itemCountryTaxRateMap) {
        this.itemCountryTaxRateMap = itemCountryTaxRateMap;
    }

    public Map<String, Double> getFulfillmentGroupPostalCodeTaxRateMap() {
        return fulfillmentGroupPostalCodeTaxRateMap;
    }

    public void setFulfillmentGroupPostalCodeTaxRateMap(Map<String, Double> fulfillmentGroupPostalCodeTaxRateMap) {
        this.fulfillmentGroupPostalCodeTaxRateMap = fulfillmentGroupPostalCodeTaxRateMap;
    }

    public Map<String, Double> getFulfillmentGroupCityTaxRateMap() {
        return fulfillmentGroupCityTaxRateMap;
    }

    public void setFulfillmentGroupCityTaxRateMap(Map<String, Double> fulfillmentGroupCityTaxRateMap) {
        this.fulfillmentGroupCityTaxRateMap = fulfillmentGroupCityTaxRateMap;
    }

    public Map<String, Double> getFulfillmentGroupStateTaxRateMap() {
        return fulfillmentGroupStateTaxRateMap;
    }

    public void setFulfillmentGroupStateTaxRateMap(Map<String, Double> fulfillmentGroupStateTaxRateMap) {
        this.fulfillmentGroupStateTaxRateMap = fulfillmentGroupStateTaxRateMap;
    }

    public Map<String, Double> getFulfillmentGroupCountryTaxRateMap() {
        return fulfillmentGroupCountryTaxRateMap;
    }

    public void setFulfillmentGroupCountryTaxRateMap(Map<String, Double> fulfillmentGroupCountryTaxRateMap) {
        this.fulfillmentGroupCountryTaxRateMap = fulfillmentGroupCountryTaxRateMap;
    }

    public Double getDefaultItemTaxRate() {
        return defaultItemTaxRate;
    }

    public void setDefaultItemTaxRate(Double defaultItemTaxRate) {
        this.defaultItemTaxRate = defaultItemTaxRate;
    }

    public Double getDefaultFulfillmentGroupTaxRate() {
        return defaultFulfillmentGroupTaxRate;
    }

    public void setDefaultFulfillmentGroupTaxRate(Double defaultFulfillmentGroupTaxRate) {
        this.defaultFulfillmentGroupTaxRate = defaultFulfillmentGroupTaxRate;
    }

}
