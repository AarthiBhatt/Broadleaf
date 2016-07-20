package org.broadleafcommerce.common.logging;

import org.apache.commons.logging.Log;
import org.broadleafcommerce.common.web.BroadleafRequestContext;


public class RequestLoggingUtil {

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
