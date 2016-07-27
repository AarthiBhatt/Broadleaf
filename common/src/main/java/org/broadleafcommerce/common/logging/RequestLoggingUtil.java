/*
 * #%L
 * BroadleafCommerce Common Libraries
 * %%
 * Copyright (C) 2009 - 2016 Broadleaf Commerce
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
package org.broadleafcommerce.common.logging;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.broadleafcommerce.common.web.BroadleafRequestContext;

import java.util.Random;

/**
 * @author Chad Harchar (charchar)
 */
public class RequestLoggingUtil {

    private static Random rand = new Random();

    public static void logInfoRequestMessage(Log logCategory, String message, Class clazz) {
        if (isRequestLoggingEnabled()) {
            logCategory.info(getRequestId() + message);
        }
    }

    public static void logDebugRequestMessage(Log logCategory, String message, Class clazz) {
        if (isRequestLoggingEnabled()) {
            logCategory.debug(getRequestId() + message);
        }
    }

    public static void logTraceRequestMessage(Log logCategory, String message, Class clazz) {
        if (isRequestLoggingEnabled()) {
            logCategory.trace(getRequestId() + message);
        }
    }

    public static void logWarnRequestMessage(Log logCategory, String message, Class clazz) {
        if (isRequestLoggingEnabled()) {
            logCategory.warn(getRequestId() + message);
        }
    }

    /**
     * Generally good request Id.    This accounts for a few developers testing with 
     * request logging at the same time without requiring full on UUID strings in the logs.
     * @return
     */
    public static String getRequestId() {
        BroadleafRequestContext brc = BroadleafRequestContext.getBroadleafRequestContext();
        if (brc != null && brc.getAdditionalProperties() != null) {
            String id = (String) brc.getAdditionalProperties().get("BLC-DEBUG-requestId");
            if (id == null) {
                int requestId = rand.nextInt(9999);
                id = requestId + "-";
                brc.getAdditionalProperties().put("BLC-DEBUG-requestId", id);
            }
            return id;
        }
        return "";
    }

    public static boolean isRequestLoggingEnabled() {
        boolean response = false;
        BroadleafRequestContext context = BroadleafRequestContext.getBroadleafRequestContext();
        if (context != null) {
            response = context.isRequestLogging();
        }
        return response;
    }
}
