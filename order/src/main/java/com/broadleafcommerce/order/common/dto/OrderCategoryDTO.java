package com.broadleafcommerce.order.common.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by brandon on 12/6/16.
 */
@Data
public class OrderCategoryDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    protected Long externalId;
    protected String name;
    protected String description;
    protected String longDescription;
    protected Date activeStartDate;
    protected Date activeEndDate;
    protected String taxCode;
    protected String inventoryType;
    protected String fulfillmentType;
    protected Map<String, String> categoryAttributes = new HashMap<String, String>();

}
