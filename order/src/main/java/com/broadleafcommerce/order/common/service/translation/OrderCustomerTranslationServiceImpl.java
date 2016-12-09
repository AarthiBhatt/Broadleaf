package com.broadleafcommerce.order.common.service.translation;

import org.springframework.stereotype.Service;

import com.broadleafcommerce.order.common.domain.OrderCustomer;
import com.broadleafcommerce.order.common.dto.OrderCustomerDTO;

@Service("blOrderCustomerTranslationService")
public class OrderCustomerTranslationServiceImpl implements OrderCustomerTranslationService {

    @Override
    public void copyOrderCustomerToDTO(OrderCustomer customer, OrderCustomerDTO dto) {
        if (customer.getExternalId() != null) {
            dto.setExternalId(customer.getExternalId());
        }
        if (customer.getFirstName() != null) {
            dto.setFirstName(customer.getFirstName());
        }
        if (customer.getLastName() != null) {
            dto.setLastName(customer.getLastName());
        }
        if (customer.getEmailAddress() != null) {
            dto.setEmailAddress(customer.getEmailAddress());
        }
        if (customer.getCustomerAttributesJson() != null) {
            dto.setCustomerAttributesJson(customer.getCustomerAttributesJson());
        }
        if (customer.getTaxExempt() != null) {
            dto.setIsTaxExempt(customer.getTaxExempt());
        }
        if (customer.getTaxExemptionCode() != null) {
            dto.setTaxExemptionCode(customer.getTaxExemptionCode());
        }
    }

    @Override
    public void copyDTOToOrderCustomer(OrderCustomerDTO dto, OrderCustomer customer) {
        if (dto.getExternalId() != null) {
            customer.setExternalId(dto.getExternalId());
        }
        if (dto.getFirstName() != null) {
            customer.setFirstName(dto.getFirstName());
        }
        if (dto.getLastName() != null) {
            customer.setLastName(dto.getLastName());
        }
        if (dto.getEmailAddress() != null) {
            customer.setEmailAddress(dto.getEmailAddress());
        }
        if (dto.getCustomerAttributesJson() != null) {
            customer.setCustomerAttributesJson(dto.getCustomerAttributesJson());
        }
        if (dto.getIsTaxExempt() != null) {
            customer.setTaxExempt(dto.getIsTaxExempt());
        }
        if (dto.getTaxExemptionCode() != null) {
            customer.setTaxExemptionCode(dto.getTaxExemptionCode());
        }
    }

}
