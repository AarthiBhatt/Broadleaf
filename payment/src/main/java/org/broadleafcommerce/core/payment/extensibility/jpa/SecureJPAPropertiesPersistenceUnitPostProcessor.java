/*
 * #%L
 * BroadleafCommerce Order
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
package org.broadleafcommerce.core.payment.extensibility.jpa;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.orm.jpa.persistenceunit.MutablePersistenceUnitInfo;
import org.springframework.orm.jpa.persistenceunit.PersistenceUnitPostProcessor;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.annotation.PostConstruct;

/**
 * Post-processes the blSecurePU properties created by this Order micro module
 * 
 * @author Phillip Verheyden (phillipuniverse)
 */
public class SecureJPAPropertiesPersistenceUnitPostProcessor implements PersistenceUnitPostProcessor {

    protected Map<String, String> persistenceUnitProperties = new HashMap<>();
    protected Map<String, String> overrideProperties = new HashMap<>();
    
    @Value("${blSecurePU.hibernate.hbm2ddl.auto}")
    protected String blSecurePUHibernateHbm2ddlAuto;
    @Value("${blSecurePU.hibernate.dialect}")
    protected String blSecurePUHibernateDialect;
    @Value("${blSecurePU.hibernate.show_sql}")
    protected String blSecurePUHibernateShow_sql;
    @Value("${blSecurePU.hibernate.cache.use_second_level_cache}")
    protected String blSecurePUHibernateCacheUse_second_level_cache;
    @Value("${blSecurePU.hibernate.cache.use_query_cache}")
    protected String blSecurePUHibernateCacheUse_query_cache;
    @Value("${blSecurePU.hibernate.hbm2ddl.import_files}")
    protected String blSecurePUHibernateHbm2ddlImport_files;
    @Value("${blSecurePU.hibernate.hbm2ddl.import_files_sql_extractor}")
    protected String blSecurePUHibernateHbm2ddlImport_files_sql_extractor;
    
    @PostConstruct
    public void populatePresetProperties() {
        if (!blSecurePUHibernateHbm2ddlAuto.startsWith("${")) persistenceUnitProperties.put("blSecurePU.hibernate.hbm2ddl.auto", blSecurePUHibernateHbm2ddlAuto);
        if (!blSecurePUHibernateDialect.startsWith("${")) persistenceUnitProperties.put("blSecurePU.hibernate.dialect", blSecurePUHibernateDialect);
        if (!blSecurePUHibernateShow_sql.startsWith("${")) persistenceUnitProperties.put("blSecurePU.hibernate.show_sql", blSecurePUHibernateShow_sql);
        if (!blSecurePUHibernateCacheUse_second_level_cache.startsWith("${")) persistenceUnitProperties.put("blSecurePU.hibernate.cache.use_second_level_cache", blSecurePUHibernateCacheUse_second_level_cache);
        if (!blSecurePUHibernateCacheUse_query_cache.startsWith("${")) persistenceUnitProperties.put("blSecurePU.hibernate.cache.use_query_cache", blSecurePUHibernateCacheUse_query_cache);
        if (!blSecurePUHibernateHbm2ddlImport_files.startsWith("${")) persistenceUnitProperties.put("blSecurePU.hibernate.hbm2ddl.import_files", blSecurePUHibernateHbm2ddlImport_files);
        if (!blSecurePUHibernateHbm2ddlImport_files_sql_extractor.startsWith("${")) persistenceUnitProperties.put("blSecurePU.hibernate.hbm2ddl.import_files_sql_extractor", blSecurePUHibernateHbm2ddlImport_files_sql_extractor);

        persistenceUnitProperties.putAll(overrideProperties);
    }
    
    @Override
    public void postProcessPersistenceUnitInfo(MutablePersistenceUnitInfo pui) {
        if (persistenceUnitProperties != null) {
            String puName = pui.getPersistenceUnitName() + ".";
            Set<String> keys = persistenceUnitProperties.keySet();
            Properties props = pui.getProperties();

            for (String key : keys) {
                if (key.startsWith(puName)){
                    String value = persistenceUnitProperties.get(key);
                    String newKey = key.substring(puName.length());
                    if ("null".equalsIgnoreCase(value)){
                        props.remove(newKey);
                    } else if (value != null && ! "".equals(value)) {
                        props.put(newKey, value);
                    }
                }
            }
            pui.setProperties(props);
        }
    }
    
    public void setPersistenceUnitProperties(Map<String, String> properties) {
        this.overrideProperties = properties;
    }
}
