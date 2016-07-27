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

import org.broadleafcommerce.common.web.BroadleafRequestContext;
import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * @author Chad Harchar (charchar)
 */
@Component("blRequestLoggingUtil")
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
