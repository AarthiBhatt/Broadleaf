package com.broadleafcommerce.order.common.service.translation;

import com.broadleafcommerce.order.common.domain.OrderCustomer;
import com.broadleafcommerce.order.common.dto.OrderCustomerDTO;

public interface OrderCustomerTranslationService {
    
    public void copyOrderCustomerToDTO(OrderCustomer customer, OrderCustomerDTO dto);
    
    public void copyDTOToOrderCustomer(OrderCustomerDTO dto, OrderCustomer customer);
}
