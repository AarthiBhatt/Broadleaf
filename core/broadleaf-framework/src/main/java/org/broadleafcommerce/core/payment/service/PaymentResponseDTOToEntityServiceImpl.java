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
package org.broadleafcommerce.core.payment.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.broadleafcommerce.common.i18n.service.ISOService;
import org.broadleafcommerce.common.payment.PaymentAdditionalFieldType;
import org.broadleafcommerce.common.payment.dto.AddressDTO;
import org.broadleafcommerce.common.payment.dto.PaymentResponseDTO;
import org.broadleafcommerce.core.order.domain.FulfillmentGroup;
import org.broadleafcommerce.core.order.domain.Order;
import com.broadleafcommerce.order.common.domain.OrderAddress;
import org.broadleafcommerce.core.order.service.FulfillmentGroupService;
import org.broadleafcommerce.core.order.service.OrderAddressService;
import org.broadleafcommerce.core.payment.domain.CustomerPayment;
import org.broadleafcommerce.core.payment.domain.OrderPayment;
import org.broadleafcommerce.profile.core.service.CountryService;
import org.broadleafcommerce.profile.core.service.CountrySubdivisionService;
import org.broadleafcommerce.profile.core.service.PhoneService;
import org.broadleafcommerce.profile.core.service.StateService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Elbert Bautista (elbertbautista)
 */
@Service("blPaymentResponseDTOToEntityService")
public class PaymentResponseDTOToEntityServiceImpl implements PaymentResponseDTOToEntityService {

    private static final Log LOG = LogFactory.getLog(PaymentResponseDTOToEntityServiceImpl.class);

    @Resource(name = "blOrderAddressService")
    protected OrderAddressService orderAddressService;

    @Resource(name = "blStateService")
    protected StateService stateService;

    @Resource(name = "blCountryService")
    protected CountryService countryService;

    @Resource(name = "blISOService")
    protected ISOService isoService;

    @Resource(name = "blPhoneService")
    protected PhoneService phoneService;

    @Resource(name = "blFulfillmentGroupService")
    protected FulfillmentGroupService fulfillmentGroupService;

    @Resource(name = "blCountrySubdivisionService")
    protected CountrySubdivisionService countrySubdivisionService;

    @Override
    public void populateBillingInfo(PaymentResponseDTO responseDTO, OrderPayment payment, OrderAddress tempBillingAddress, boolean isUseBillingAddressFromGateway) {
        OrderAddress billingAddress = tempBillingAddress;
        if (responseDTO.getBillTo() != null && isUseBillingAddressFromGateway) {
            billingAddress = orderAddressService.create();
            AddressDTO<PaymentResponseDTO> billToDTO = responseDTO.getBillTo();
            populateAddressInfo(billToDTO, billingAddress);
        }

        payment.setBillingAddress(billingAddress);
    }

    @Override
    public void populateShippingInfo(PaymentResponseDTO responseDTO, Order order) {
        FulfillmentGroup shippableFulfillmentGroup = fulfillmentGroupService.getFirstShippableFulfillmentGroup(order);
        OrderAddress shippingAddress = null;
        if (responseDTO.getShipTo() != null && shippableFulfillmentGroup != null) {
            shippingAddress = orderAddressService.create();
            AddressDTO<PaymentResponseDTO> shipToDTO = responseDTO.getShipTo();
            populateAddressInfo(shipToDTO, shippingAddress);

            shippableFulfillmentGroup = fulfillmentGroupService.findFulfillmentGroupById(shippableFulfillmentGroup.getId());
            if (shippableFulfillmentGroup != null) {
                shippableFulfillmentGroup.setAddress(shippingAddress);
                fulfillmentGroupService.save(shippableFulfillmentGroup);
            }
        }
    }

    @Override
    public void populateAddressInfo(AddressDTO<PaymentResponseDTO> dto, OrderAddress address) {
        address.setFirstName(dto.getAddressFirstName());
        address.setLastName(dto.getAddressLastName());
        address.setFullName(dto.getAddressFirstName() + " " + dto.getAddressLastName());
        address.setAddressLine1(dto.getAddressLine1());
        address.setAddressLine2(dto.getAddressLine2());
        address.setCityLocality(dto.getAddressCityLocality());
        address.setStateProvinceRegion(dto.getAddressStateRegion());
        address.setPostalCode(dto.getAddressPostalCode());
        address.setCountryCode(dto.getAddressCountryCode());

        if (dto.getAddressPhone() != null) {
            address.setPhone(dto.getAddressPhone());
        }
        if (dto.getAddressEmail() != null) {
            address.setEmailAddress(dto.getAddressEmail());
        }
        if (dto.getAddressCompanyName() != null) {
            address.setCompanyName(dto.getAddressCompanyName());
        }
    }

    @Override
    public void populateCustomerPaymentToken(PaymentResponseDTO responseDTO, CustomerPayment customerPayment) {
        if (responseDTO.getPaymentToken() != null) {
            customerPayment.setPaymentToken(responseDTO.getPaymentToken());
        } else if (responseDTO.getResponseMap().containsKey(PaymentAdditionalFieldType.TOKEN.getType())) {
            //handle legacy additional fields map
            customerPayment.setPaymentToken(responseDTO.getResponseMap().get(PaymentAdditionalFieldType.TOKEN.getType()));
        } else if (responseDTO.getCreditCard() != null) {
            //handle higher PCI level compliance scenarios
            customerPayment.setPaymentToken(responseDTO.getCreditCard().getCreditCardNum());
        }
    }

}
