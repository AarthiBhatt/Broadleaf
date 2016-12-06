package com.broadleafcommerce.order.common.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by brandon on 12/6/16.
 */
@Data
public class OrderProductDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    protected Long externalId;
    protected String manufacturer;
    protected Boolean isFeaturedProduct = false;
    protected String promoMessage;
    protected OrderSkuDTO defaultSku;
    protected List<OrderSkuDTO> skus = new ArrayList<OrderSkuDTO>();
    protected OrderCategoryDTO defaultCategory;
    protected List<OrderCategoryDTO> allParentCategories = new ArrayList<OrderCategoryDTO>();
    protected Map<String, String> productAttributes = new HashMap<String, String>();

}
