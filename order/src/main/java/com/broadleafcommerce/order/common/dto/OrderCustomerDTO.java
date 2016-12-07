package com.broadleafcommerce.order.common.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by brandon on 12/6/16.
 */
@Data
public class OrderCustomerDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    protected Long externalId;
    protected String firstName;
    protected String lastName;
    protected String emailAddress;
    protected Boolean isTaxExempt = false;
    protected String taxExemptionCode;
    protected String customerAttributesJson;

}
