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

import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * @author Mark Curtis
 */
public class TwilioSmsServiceImplTest {

    @Test
    public void sendMessage() throws Exception {

        String accountId = "AC3257b7791eac488777248f4eb0356cdc";
        String authToken = "ba71bcfb87effc4ab27a53a05a56547c";

        SmsService smsService = new TwilioSmsServiceImpl(accountId, authToken);

        assertTrue("SMS service should return true for successful message send", smsService.sendMessage("+17196440024", "Hay buddy, how are you?"));

    }

}