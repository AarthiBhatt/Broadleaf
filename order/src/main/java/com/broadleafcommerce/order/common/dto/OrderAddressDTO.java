package com.broadleafcommerce.order.common.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by brandon on 12/6/16.
 */
@Data
public class OrderAddressDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    protected Long externalId;
    protected String fullName;
    protected String firstName;
    protected String lastName;
    protected String companyName;
    protected String addressLine1;
    protected String addressLine2;
    protected String addressLine3;
    protected String cityLocality;
    protected String stateProvinceRegion;
    protected String countryCode;
    protected String postalCode;
    protected String phone;
    protected String emailAddress;

}
