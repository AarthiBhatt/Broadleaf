package org.broadleafcommerce.common.logging;

import org.apache.commons.logging.Log;


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
        // TODO:  Check BLC Request Context
        return true;
    }
}
