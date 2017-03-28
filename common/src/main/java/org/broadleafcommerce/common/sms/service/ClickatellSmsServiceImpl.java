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
package org.broadleafcommerce.common.sms.service;

import org.apache.log4j.Logger;
import org.broadleafcommerce.common.sms.domain.clickatell.request.SendMessageRequestType;
import org.broadleafcommerce.common.sms.domain.clickatell.response.SendMessagesResponseType;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;

/**
 * @author Mark Curtis
 */
public class ClickatellSmsServiceImpl extends SmsService {

    private static final Logger LOGGER = Logger.getLogger(ClickatellSmsServiceImpl.class);

    public ClickatellSmsServiceImpl(String accountId, String authToken) {
        super(accountId, authToken);
    }

    @Override
    public boolean sendMessage(String mobileNumber, String message) {
        SendMessageRequestType sendMessageRequest = new SendMessageRequestType();
        sendMessageRequest.setContent(message);
        sendMessageRequest.setTo(mobileNumber);
        sendMessageRequest.setBinary("true");
//        sendMessageRequest.setClientMessageId("");
//        sendMessageRequest.setScheduledDeliveryTime("");
//        sendMessageRequest.setUserDataHeader("");
//        sendMessageRequest.setValidityPeriod("0");
        sendMessageRequest.setCharset("UTF-8");

        URI url = null;
        try {
            url = new URI("https://platform.clickatell.com/messages");

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        RestTemplate restTemplate = new RestTemplate();

        MultiValueMap headers = new HttpHeaders();
        headers.put("Content-Type", Arrays.asList("application/xml"));
        headers.put("Accept", Arrays.asList("application/xml"));
        headers.put("Authorization", Arrays.asList(getAuthToken()));
        HttpEntity httpEntity = new HttpEntity(sendMessageRequest, headers);

        try {

            JAXBContext jaxbContext = JAXBContext.newInstance(SendMessageRequestType.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.marshal(sendMessageRequest, System.out);

        } catch (JAXBException e) {
            e.printStackTrace();

        }

        ResponseEntity<SendMessagesResponseType>  response = null;
        try {
            response = restTemplate.postForEntity(url, httpEntity, SendMessagesResponseType.class);

        } catch (RestClientException e) {
            LOGGER.error(e);

        }

        return response!=null && response.getStatusCode().is2xxSuccessful();

    }
}
