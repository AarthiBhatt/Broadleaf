/*
 * #%L
 * BroadleafCommerce Common Libraries
 * %%
 * Copyright (C) 2009 - 2016 Broadleaf Commerce
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *       http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
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
