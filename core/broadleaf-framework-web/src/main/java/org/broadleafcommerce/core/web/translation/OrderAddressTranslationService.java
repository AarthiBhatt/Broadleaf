package org.broadleafcommerce.core.web.translation;

import org.broadleafcommerce.profile.core.domain.Address;

import com.broadleafcommerce.order.common.domain.OrderAddress;

public interface OrderAddressTranslationService {
    public Address convertOrderAddressToAddress(OrderAddress orderAddress);
    public OrderAddress convertAddressToOrderAddress(Address address);
}
