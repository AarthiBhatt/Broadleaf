package org.broadleafcommerce.common.logging;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Chad Harchar (charchar)
 */
public class LogCategory {

    private Log log;

    public static final LogCategory BL_OFFER = new LogCategory("blOfferLog");
    public static final LogCategory BL_RESOURCE_RESOLVER = new LogCategory("blResourceResolverLog");
    public static final LogCategory BL_REQUEST_PROCESSING = new LogCategory("blRequestProcessing");

    public LogCategory(String logName) {
        log = LogFactory.getLog("blOfferLog");
    }

    public Log getLog() {
        return log;
    }

    public void setLog(Log log) {
        this.log = log;
    }

}
