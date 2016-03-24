/*
 * #%L
 * BroadleafCommerce Common Libraries
 * %%
 * Copyright (C) 2009 - 2013 Broadleaf Commerce
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
package org.broadleafcommerce.common.web;

import org.thymeleaf.IEngineConfiguration;
import org.thymeleaf.cache.ICacheEntryValidity;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.AbstractTemplateResolver;
import org.thymeleaf.templateresource.ITemplateResource;

import java.util.Map;


/**
 * Placeholder component to support a custom TemplateResolver.
 * 
 * Utilized by the Broadleaf Commerce CustomTemplate extension to introduce themes at the DB level.
 *
 * @author bpolster
 */
public class NullBroadleafTemplateResolver extends AbstractTemplateResolver {

    public NullBroadleafTemplateResolver() {
        super();
        setOrder(9999);
    }
    
    @Override
    protected ITemplateResource computeTemplateResource(IEngineConfiguration configuration, String ownerTemplate, String template, Map<String, Object> templateResolutionAttributes) {
        return null;
    }

    @Override
    protected TemplateMode computeTemplateMode(IEngineConfiguration configuration, String ownerTemplate, String template, Map<String, Object> templateResolutionAttributes) {
        return null;
    }

    @Override
    protected ICacheEntryValidity computeValidity(IEngineConfiguration configuration, String ownerTemplate, String template, Map<String, Object> templateResolutionAttributes) {
        return null;
    }
    
//    @Override
//    public Integer getOrder() {
//        return 9999;
//    }
//
//    @Override
//    public TemplateResolution resolveTemplate(TemplateProcessingParameters templateProcessingParameters) {
//        return null;
//    }
//
//    @Override
//    public void initialize() {
//
//    }
    
    
}
