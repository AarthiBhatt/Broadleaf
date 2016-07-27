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

import org.broadleafcommerce.common.web.BroadleafRequestContext;

import java.util.Random;

/**
 * @author Chad Harchar (charchar)
 */
public class RequestLoggingUtil {

    private static Random rand = new Random();

    public void logInfo(LogCategory logCategory, Class clazz, String message) {
        if (isRequestLoggingEnabled()) {
            logCategory.getLog().info(getRequestPrefix(clazz) + message);
        }
    }

    public void logDebug(LogCategory logCategory, Class clazz, String message) {
        if (isRequestLoggingEnabled()) {
            logCategory.getLog().debug(getRequestPrefix(clazz) + message);
        }
    }

    public void logTrace(LogCategory logCategory, Class clazz, String message) {
        if (isRequestLoggingEnabled()) {
            logCategory.getLog().trace(getRequestPrefix(clazz) + message);
        }
    }

    public void logWarn(LogCategory logCategory, Class clazz, String message) {
        if (isRequestLoggingEnabled()) {
            logCategory.getLog().warn(getRequestPrefix(clazz) + message);
        }
    }

    public String getRequestPrefix(Class clazz) {
        return clazz.getName() + " - " + getRequestId();
    }

    /**
     * Generally good request Id.    This accounts for a few users doing request level logging 
     * with a reasonable assurance that we can separate the logs for each user without having
     * large UUIDs in each message. 
     * 
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
