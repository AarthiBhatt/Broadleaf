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


public class RequestLoggingUtil {

    public static final Log BL_OFFER_LOG = LogFactory.getLog("blOfferLog");

    public static void logInfoRequestMessage(String message, Log log) {
        if (isRequestLoggingEnabled()) {
            log.info(message);
        }
    }

    public static void logDebugRequestMessage(String message, Log log) {
        if (isRequestLoggingEnabled()) {
            log.debug(message);
        }
    }

    public static void logTraceRequestMessage(String message, Log log) {
        if (isRequestLoggingEnabled()) {
            log.trace(message);
        }
    }

    public static void logWarnRequestMessage(String message, Log log) {
        if (isRequestLoggingEnabled()) {
            log.warn(message);
        }
    }

    public static boolean isRequestLoggingEnabled() {
        return BroadleafRequestContext.getBroadleafRequestContext().isRequestLogging();
    }
}
