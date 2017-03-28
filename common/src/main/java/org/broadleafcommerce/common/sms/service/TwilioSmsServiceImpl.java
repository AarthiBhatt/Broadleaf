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

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

/**
 * @author Mark Curtis
 */
public class TwilioSmsServiceImpl extends SmsService {

    public static final String FROM_NUMBER = "+14152879749";

    public TwilioSmsServiceImpl(String accountId, String authToken) {
        super(accountId, authToken);
        Twilio.init(getAccountId(), getAuthToken());

    }

    @Override
    public boolean sendMessage(String mobileNumber, String messageStr) {

        Message message = Message
                .creator(new PhoneNumber(mobileNumber), new PhoneNumber(FROM_NUMBER), messageStr)
                .setMediaUrl("https://c1.staticflickr.com/3/2899/14341091933_1e92e62d12_b.jpg")
                .create();

        return message.getErrorCode()==null;

    }
}
