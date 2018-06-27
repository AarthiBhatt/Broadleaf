/*
 * #%L
 * BroadleafCommerce Framework
 * %%
 * Copyright (C) 2009 - 2018 Broadleaf Commerce
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
package org.broadleafcommerce.core.catalog.domain;

import org.broadleafcommerce.common.copy.CreateResponse;
import org.broadleafcommerce.common.copy.MultiTenantCloneable;
import org.broadleafcommerce.common.copy.MultiTenantCopyContext;
import org.broadleafcommerce.common.extensibility.jpa.copy.DirectCopyTransform;
import org.broadleafcommerce.common.extensibility.jpa.copy.DirectCopyTransformMember;
import org.broadleafcommerce.common.extensibility.jpa.copy.DirectCopyTransformTypes;
import org.broadleafcommerce.common.presentation.AdminPresentation;
import org.broadleafcommerce.common.presentation.client.VisibilityEnum;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.*;
import org.hibernate.annotations.Index;
import org.hibernate.annotations.Parameter;

import javax.persistence.CascadeType;
import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name="SKU_CROSS_SALE")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region="blStandardElements")
@DirectCopyTransform({
        @DirectCopyTransformMember(templateTokens = DirectCopyTransformTypes.SANDBOX, skipOverlaps=true),
        @DirectCopyTransformMember(templateTokens = DirectCopyTransformTypes.MULTITENANT_CATALOG)
})
public class CrossSaleSkuXref implements Serializable, RelatedSku, MultiTenantCloneable<CrossSaleSkuXref> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator= "CrossSaleSkuId")
    @GenericGenerator(
        name="CrossSaleSkuId",
        strategy="org.broadleafcommerce.common.persistence.IdOverrideTableGenerator",
        parameters = {
            @Parameter(name="segment_value", value="CrossSaleSkuImpl"),
            @Parameter(name="entity_name", value="com.drf.core.catalog.domain.CrossSaleSkuXrefImpl")
        }
    )
    @Column(name = "CROSS_SALE_SKU_ID")
    protected Long id;

    @Column(name = "PROMOTION_MESSAGE")
    @AdminPresentation(friendlyName = "Promotion Message", largeEntry=true)
    protected String promotionMessage;

    @Column(name = "SEQUENCE", precision = 10, scale = 6)
    @AdminPresentation(visibility = VisibilityEnum.HIDDEN_ALL)
    protected BigDecimal sequence;

    @ManyToOne(targetEntity = SkuImpl.class, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "SKU_ID")
    @Index(name="CROSSSALE_SKU_INDEX", columnNames={"SKU_ID"})
    protected Sku sku;

    @ManyToOne(targetEntity = SkuImpl.class, optional=false)
    @JoinColumn(name = "RELATED_SALE_SKU_ID", referencedColumnName = "SKU_ID")
    @Index(name="CROSSSALE_SKU_RELATED_INDEX", columnNames={"RELATED_SALE_SKU_ID"})
    protected Sku relatedSaleSku;


    @Override
    public Long getId() {
        return id;
    }


    @Override
    public void setId(Long id) {
        this.id = id;
    }


    @Override
    public String getPromotionMessage() {
        return promotionMessage;
    }


    @Override
    public void setPromotionMessage(String promotionMessage) {
        this.promotionMessage = promotionMessage;
    }


    @Override
    public BigDecimal getSequence() {
        return sequence;
    }


    @Override
    public void setSequence(BigDecimal sequence) {
        this.sequence = sequence;
    }


    @Override
    public Sku getSku() {
        return sku;
    }


    @Override
    public void setSku(Sku sku) {
        this.sku = sku;
    }


    @Override
    public Sku getRelatedSaleSku() {
        return relatedSaleSku;
    }


    @Override
    public void setRelatedSaleSku(Sku relatedSaleSku) {
        this.relatedSaleSku = relatedSaleSku;
    }

    @Override
    public <G extends CrossSaleSkuXref> CreateResponse<G> createOrRetrieveCopyInstance(MultiTenantCopyContext context) throws CloneNotSupportedException {
        CreateResponse<G> createResponse = context.createOrRetrieveCopyInstance(this);
        if (createResponse.isAlreadyPopulated()) {
            return createResponse;
        }
        CrossSaleSkuXref cloned = createResponse.getClone();
        if (sku != null) {
            cloned.setSku(sku.createOrRetrieveCopyInstance(context).getClone());
        }
        cloned.setPromotionMessage(promotionMessage);
        cloned.setSequence(sequence);
        if (relatedSaleSku != null) {
            cloned.setRelatedSaleSku(relatedSaleSku.createOrRetrieveCopyInstance(context).getClone());
        }
        return createResponse;
    }

}
