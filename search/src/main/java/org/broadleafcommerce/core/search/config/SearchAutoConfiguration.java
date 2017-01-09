/*
 * #%L
 * BroadleafCommerce Search
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
/**
 * 
 */
package org.broadleafcommerce.core.search.config;

import org.broadleafcommerce.common.extensibility.FrameworkXmlBeanDefinitionReader;
import org.broadleafcommerce.core.search.service.solr.FileSystemSolrIndexStatusProviderImpl;
import org.broadleafcommerce.core.search.service.solr.index.SolrIndexStatusProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

import java.util.Arrays;
import java.util.List;

/**
 * 
 * 
 * @author Phillip Verheyden (phillipuniverse)
 */
@Configuration
@ImportResource(value = {
        "classpath:/bl-search-applicationContext-persistence.xml",
        "classpath:/bl-search-applicationContext.xml"
}, reader = FrameworkXmlBeanDefinitionReader.class)
@ComponentScan("org.broadleafcommerce.core.search")
public class SearchAutoConfiguration {

    @Bean
    public List<SolrIndexStatusProvider> blSolrIndexStatusProviders() {
        return Arrays.asList(blFileSystemSolrIndexStatusProvider());
    }
    
    @Bean
    public SolrIndexStatusProvider blFileSystemSolrIndexStatusProvider() {
        return new FileSystemSolrIndexStatusProviderImpl();
    }
}
