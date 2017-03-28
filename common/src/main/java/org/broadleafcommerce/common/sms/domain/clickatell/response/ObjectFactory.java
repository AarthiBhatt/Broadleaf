/*
 * #%L
 * BroadleafCommerce Common Libraries
 * %%
 * Copyright (C) 2009 - 2017 Broadleaf Commerce
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

package org.broadleafcommerce.common.sms.domain.clickatell.response;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the response package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _SendMessagesResponse_QNAME = new QName("", "SendMessagesResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: response
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link SendMessagesResponseType }
     *
     */
    public SendMessagesResponseType createSendMessagesResponseType() {
        return new SendMessagesResponseType();
    }

    /**
     * Create an instance of {@link MessagesType }
     *
     */
    public MessagesType createMessagesType() {
        return new MessagesType();
    }

    /**
     * Create an instance of {@link MessageType }
     *
     */
    public MessageType createMessageType() {
        return new MessageType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SendMessagesResponseType }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "", name = "SendMessagesResponse")
    public JAXBElement<SendMessagesResponseType> createSendMessagesResponse(SendMessagesResponseType value) {
        return new JAXBElement<SendMessagesResponseType>(_SendMessagesResponse_QNAME, SendMessagesResponseType.class, null, value);
    }

}
