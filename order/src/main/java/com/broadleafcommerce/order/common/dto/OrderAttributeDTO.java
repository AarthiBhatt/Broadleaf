//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.broadleafcommerce.order.common.dto;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.broadleafcommerce.common.api.APIWrapper;
import org.broadleafcommerce.common.api.BaseWrapper;
import org.broadleafcommerce.core.order.domain.OrderAttribute;

@XmlRootElement(
        name = "orderAttribute"
)
@XmlAccessorType(XmlAccessType.FIELD)
public class OrderAttributeDTO extends BaseWrapper implements APIWrapper<OrderAttribute> {
    @XmlElement
    protected Long id;
    @XmlElement
    protected String name;
    @XmlElement
    protected String value;
    @XmlElement
    protected Long orderId;

    public OrderAttributeDTO() {
    }

    public void wrapDetails(OrderAttribute model, HttpServletRequest request) {
        this.id = model.getId();
        this.name = model.getName();
        this.value = model.getValue();
        this.orderId = model.getOrder().getId();
    }

    public void wrapSummary(OrderAttribute model, HttpServletRequest request) {
        this.wrapDetails(model, request);
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Long getOrderId() {
        return this.orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
}
