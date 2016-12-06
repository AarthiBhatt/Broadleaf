package com.broadleafcommerce.order.common.dto;

import lombok.Data;

import org.broadleafcommerce.common.locale.domain.Locale;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by brandon on 12/6/16.
 */
@Data
public class OrderCustomerDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    protected Long externalId;
    protected String username;
    protected String emailAddress;
    protected String firstName;
    protected String lastName;
    protected Boolean receiveEmail = true;
    protected Boolean registered = false;
    protected Boolean deactivated = false;
    protected Locale customerLocale;
    protected Map<String, String> customerAttributes = new HashMap<>();
    protected List<OrderAddressDTO> customerAddresses = new ArrayList<>();
    protected List<String> customerPhones = new ArrayList<>();
    protected Boolean isTaxExempt = false;
    protected String taxExemptionCode;
    protected Boolean anonymous;

}
