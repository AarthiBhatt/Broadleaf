/*
 * #%L
 * broadleaf-theme
 * %%
 * Copyright (C) 2009 - 2015 Broadleaf Commerce
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
package org.broadleafcommerce.common.web.resource.resolver;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.broadleafcommerce.common.logging.LogCategory;
import org.broadleafcommerce.common.logging.RequestLoggingUtil;
import org.springframework.core.Ordered;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.resource.PathResourceResolver;
import org.springframework.web.servlet.resource.ResourceResolverChain;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * Wraps Spring's {@link PathResourceResolver} for ordering purposes.
 *  
 * @author Brian Polster
 * @since Broadleaf 4.0
 */
@Component("blPathResourceResolver")
public class BroadleafPathResourceResolver extends PathResourceResolver implements Ordered {

    protected static final Log LOG = LogFactory.getLog(BroadleafPathResourceResolver.class);

    private int order = BroadleafResourceResolverOrder.BLC_PATH_RESOURCE_RESOLVER;

    @javax.annotation.Resource(name = "blRequestLoggingUtil")
    protected RequestLoggingUtil requestLoggingUtil;

    @Override
    protected Resource resolveResourceInternal(HttpServletRequest request, String requestPath,
            List<? extends Resource> locations, ResourceResolverChain chain) {

        Resource retRes = super.resolveResourceInternal(request, requestPath, locations, chain);

        if (requestLoggingUtil.isRequestLoggingEnabled()) {
            if (retRes == null) {
                requestLoggingUtil.logDebug(LogCategory.BL_RESOURCE_RESOLVER, getClass(),
                        String.format("Request path '%s' resolved to null resource.", requestPath));
            } else {
                requestLoggingUtil.logDebug(LogCategory.BL_RESOURCE_RESOLVER, getClass(),
                        String.format("Request path '%s' resolved to filename, description ",
                                requestPath, retRes.getFilename(), retRes.getDescription()));
            }

        }

        return retRes;
    }

    @Override
    protected String resolveUrlPathInternal(String resourcePath, List<? extends Resource> locations,
            ResourceResolverChain chain) {

        String returnPath = super.resolveUrlPathInternal(resourcePath, locations, chain);

        if (requestLoggingUtil.isRequestLoggingEnabled()) {
            requestLoggingUtil.logDebug(LogCategory.BL_RESOURCE_RESOLVER, getClass(),
                    String.format("Resource path '%s' resolved to path '%s'", resourcePath, returnPath));
        }

        return returnPath;
    }

    @Override
    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }


}
