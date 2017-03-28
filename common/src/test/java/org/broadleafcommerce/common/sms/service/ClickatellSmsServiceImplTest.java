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
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * @author Mark Curtis
 */
public class ClickatellSmsServiceImplTest {

    private static final Logger LOGGER = Logger.getLogger(ClickatellSmsServiceImplTest.class);

    @Test
    public void sendMessage() throws Exception {

        LOGGER.debug("Starting...");

        String auth = "7A4fH5IcTVGUYVrAPh73JA==";

        SmsService smsService = new ClickatellSmsServiceImpl(SmsService.NoAccountId, auth);
        assertTrue("SMS service should return true for successful message send", smsService.sendMessage("+17196440024", "This is a message"));

    }
}