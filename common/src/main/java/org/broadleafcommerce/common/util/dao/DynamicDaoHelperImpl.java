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
package org.broadleafcommerce.common.util.dao;

import org.apache.commons.collections4.map.LRUMap;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.broadleafcommerce.common.exception.ExceptionHelper;
import org.broadleafcommerce.common.exception.ProxyDetectionException;
import org.broadleafcommerce.common.presentation.AdminPresentationClass;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.ejb.HibernateEntityManager;
import org.hibernate.mapping.PersistentClass;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.type.Type;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ReflectionUtils;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.EntityManager;

import javassist.util.proxy.ProxyFactory;


public class DynamicDaoHelperImpl implements DynamicDaoHelper {

    private static final Log LOG = LogFactory.getLog(DynamicDaoHelperImpl.class);
    public static final Object LOCK_OBJECT = new Object();
    public static final Map<SessionFactory, Map<Class<?>, Class<?>[]>> POLYMORPHIC_ENTITY_CACHE = new LRUMap<>(1000);
    public static final Map<SessionFactory, Map<Class<?>, Class<?>[]>> POLYMORPHIC_ENTITY_CACHE_WO_EXCLUSIONS = new LRUMap<>(1000);
    public static final String JAVASSIST_PROXY_KEY_PHRASE = "_$$_";

    public static Class<?> getNonProxyImplementationClassIfNecessary(Class<?> candidate) {
        Class<?> response = candidate;
        //TODO Although unusual, there are other proxy types in Hibernate than just javassist proxies (Broadleaf defaults to javassist).
        //At some point, should try to account for those here in a performant way.
        if (ProxyFactory.isProxyClass(candidate)) {
            //TODO This is not ideal. While this works consistently, I don't think it's guaranteed that the proxy classname
            //will always have this format in the future. We'll at least throw an exception if our formatting detection fails
            //so that we're aware of it.
            if (!candidate.getName().contains(JAVASSIST_PROXY_KEY_PHRASE)) {
                throw new ProxyDetectionException(String.format("Cannot determine the original implementation class for " +
                        "the javassist proxy. Expected to find the keyphrase (%s) in the proxy classname (%s).",
                        JAVASSIST_PROXY_KEY_PHRASE, candidate.getName()));
            }
            String implName = candidate.getName().substring(0, candidate.getName().lastIndexOf(JAVASSIST_PROXY_KEY_PHRASE));
            try {
                response = Class.forName(implName);
            } catch (ClassNotFoundException e) {
                throw ExceptionHelper.refineException(e);
            }
        }
        return response;
    }
    
    @Override
    public Class<?>[] getAllPolymorphicEntitiesFromCeiling(Class<?> ceilingClass, SessionFactory sessionFactory,
            boolean includeUnqualifiedPolymorphicEntities, boolean useCache) {
        ceilingClass = getNonProxyImplementationClassIfNecessary(ceilingClass);
        Class<?>[] cache = null;
        synchronized(LOCK_OBJECT) {
            if (useCache) {
                if (includeUnqualifiedPolymorphicEntities) {
                    cache = getCachedPolymorphicEntityList(POLYMORPHIC_ENTITY_CACHE, sessionFactory, ceilingClass);
                } else {
                    cache = getCachedPolymorphicEntityList(POLYMORPHIC_ENTITY_CACHE_WO_EXCLUSIONS, sessionFactory, ceilingClass);
                }
            }
            if (cache == null) {
                List<Class<?>> entities = new ArrayList<>();
                for (Object item : sessionFactory.getAllClassMetadata().values()) {
                    ClassMetadata metadata = (ClassMetadata) item;
                    Class<?> mappedClass = metadata.getMappedClass();
                    if (mappedClass != null && ceilingClass.isAssignableFrom(mappedClass)) {
                        entities.add(mappedClass);
                    }
                }
                Class<?>[] sortedEntities = sortEntities(ceilingClass, entities);

                List<Class<?>> filteredSortedEntities = new ArrayList<>();

                for (int i = 0; i < sortedEntities.length; i++) {
                    Class<?> item = sortedEntities[i];
                    if (includeUnqualifiedPolymorphicEntities) {
                        filteredSortedEntities.add(sortedEntities[i]);
                    } else {
                        if (isExcludeClassFromPolymorphism(item)) {
                            continue;
                        } else {
                            filteredSortedEntities.add(sortedEntities[i]);
                        }
                    }
                }

                Class<?>[] filteredEntities = new Class<?>[filteredSortedEntities.size()];
                filteredEntities = filteredSortedEntities.toArray(filteredEntities);
                cache = filteredEntities;
                if (includeUnqualifiedPolymorphicEntities) {
                    Map<Class<?>, Class<?>[]> polymorphicEntityMap = buildPolymorphicEntityMap(POLYMORPHIC_ENTITY_CACHE.get(sessionFactory), ceilingClass, filteredEntities);
                    POLYMORPHIC_ENTITY_CACHE.put(sessionFactory, polymorphicEntityMap);
                } else {
                    Map<Class<?>, Class<?>[]> polymorphicEntityMap = buildPolymorphicEntityMap(POLYMORPHIC_ENTITY_CACHE_WO_EXCLUSIONS.get(sessionFactory), ceilingClass, filteredEntities);
                    POLYMORPHIC_ENTITY_CACHE_WO_EXCLUSIONS.put(sessionFactory, polymorphicEntityMap);
                }
            }
        }

        return cache;
    }

    protected Class<?>[] getCachedPolymorphicEntityList(Map<SessionFactory, Map<Class<?>, Class<?>[]>> polymorphicEntityCache,
            SessionFactory sessionFactory, Class<?> ceilingClass) {
        Map<Class<?>, Class<?>[]> polymorphicEntityMap = polymorphicEntityCache.get(sessionFactory);

        return polymorphicEntityMap == null ? null : polymorphicEntityMap.get(ceilingClass);
    }

    protected Map<Class<?>, Class<?>[]> buildPolymorphicEntityMap(Map<Class<?>, Class<?>[]> polymorphicEntityMap,
            Class<?> ceilingClass, Class<?>[] filteredEntities) {
        if (polymorphicEntityMap == null) {
            polymorphicEntityMap = new LRUMap<>(1000);
        }

        polymorphicEntityMap.put(ceilingClass, filteredEntities);
        return polymorphicEntityMap;
    }

    @Override
    public Class<?>[] getUpDownInheritance(Class<?> testClass, SessionFactory sessionFactory,
                boolean includeUnqualifiedPolymorphicEntities, boolean useCache, EJB3ConfigurationDao ejb3ConfigurationDao) {
        Class<?>[] pEntities = getAllPolymorphicEntitiesFromCeiling(testClass, sessionFactory, includeUnqualifiedPolymorphicEntities, useCache);
        if (ArrayUtils.isEmpty(pEntities)) {
            return pEntities;
        }
        Class<?> topConcreteClass = pEntities[pEntities.length - 1];
        List<Class<?>> temp = new ArrayList<>(pEntities.length);
        temp.addAll(Arrays.asList(pEntities));
        Collections.reverse(temp);
        boolean eof = false;
        while (!eof) {
            Class<?> superClass = topConcreteClass.getSuperclass();
            PersistentClass persistentClass = ejb3ConfigurationDao.getConfiguration().getClassMapping(superClass.getName());
            if (persistentClass == null) {
                eof = true;
            } else {
                temp.add(0, superClass);
                topConcreteClass = superClass;
            }
        }

        return temp.toArray(new Class<?>[temp.size()]);
    }
    
    @Override
    public Class<?>[] sortEntities(Class<?> ceilingClass, List<Class<?>> entities) {
        /*
         * Sort entities with the most derived appearing first
         */
        Class<?>[] sortedEntities = new Class<?>[entities.size()];
        List<Class<?>> stageItems = new ArrayList<>();
        stageItems.add(ceilingClass);
        int j = 0;
        while (j < sortedEntities.length) {
            List<Class<?>> newStageItems = new ArrayList<>();
            boolean topLevelClassFound = false;
            for (Class<?> stageItem : stageItems) {
                Iterator<Class<?>> itr = entities.iterator();
                while(itr.hasNext()) {
                    Class<?> entity = itr.next();
                    checkitem: {
                        if (ArrayUtils.contains(entity.getInterfaces(), stageItem) || entity.equals(stageItem)) {
                            topLevelClassFound = true;
                            break checkitem;
                        }

                        if (topLevelClassFound) {
                            continue;
                        }

                        if (entity.getSuperclass().equals(stageItem) && j > 0) {
                            break checkitem;
                        }

                        continue;
                    }
                    sortedEntities[j] = entity;
                    itr.remove();
                    j++;
                    newStageItems.add(entity);
                }
            }
            if (newStageItems.isEmpty()) {
                throw new IllegalArgumentException("There was a gap in the inheritance hierarchy for (" + ceilingClass.getName() + ")");
            }
            stageItems = newStageItems;
        }
        ArrayUtils.reverse(sortedEntities);
        return sortedEntities;
    }
    
    @Override
    public boolean isExcludeClassFromPolymorphism(Class<?> clazz) {
        //We filter out abstract classes because they can't be instantiated.
        if (Modifier.isAbstract(clazz.getModifiers())) {
            return true;
        }

        //We filter out classes that are marked to exclude from polymorphism
        AdminPresentationClass adminPresentationClass = AnnotationUtils.findAnnotation(clazz, AdminPresentationClass.class);
        if (adminPresentationClass == null) {
            return false;
        } else if (adminPresentationClass.excludeFromPolymorphism()) {
            return true;
        }
        return false;
    }
    
    @Override
    public Map<String, Object> getIdMetadata(Class<?> entityClass, HibernateEntityManager entityManager) {
        entityClass = getNonProxyImplementationClassIfNecessary(entityClass);
        Map<String, Object> response = new HashMap<>();
        SessionFactory sessionFactory = entityManager.getSession().getSessionFactory();
        
        ClassMetadata metadata = sessionFactory.getClassMetadata(entityClass);
        if (metadata == null) {
            return null;
        }
        
        String idProperty = metadata.getIdentifierPropertyName();
        response.put("name", idProperty);
        Type idType = metadata.getIdentifierType();
        response.put("type", idType);

        return response;
    }

    @Override
    public List<String> getPropertyNames(Class<?> entityClass, HibernateEntityManager entityManager) {
        entityClass = getNonProxyImplementationClassIfNecessary(entityClass);
        ClassMetadata metadata = getSessionFactory(entityManager).getClassMetadata(entityClass);
        List<String> propertyNames = new ArrayList<>();
        Collections.addAll(propertyNames, metadata.getPropertyNames());
        return propertyNames;
    }

    @Override
    public List<Type> getPropertyTypes(Class<?> entityClass, HibernateEntityManager entityManager) {
        entityClass = getNonProxyImplementationClassIfNecessary(entityClass);
        ClassMetadata metadata = getSessionFactory(entityManager).getClassMetadata(entityClass);
        List<Type> propertyTypes = new ArrayList<>();
        Collections.addAll(propertyTypes, metadata.getPropertyTypes());
        return propertyTypes;
    }

    @Override
    public SessionFactory getSessionFactory(HibernateEntityManager entityManager) {
        return entityManager.getSession().getSessionFactory();
    }

    @Override
    public Serializable getIdentifier(Object entity, EntityManager em) {
        Class<?> entityClass = getNonProxyImplementationClassIfNecessary(entity.getClass());
        if (entityClass.getAnnotation(Entity.class) != null) {
            Field idField = getIdField(entityClass, em);
            try {
                return (Serializable) idField.get(entity);
            } catch (IllegalAccessException e) {
                throw ExceptionHelper.refineException(e);
            }
        }
        return null;
    }

    @Override
    public Serializable getIdentifier(Object entity, Session session) {
        Class<?> entityClass = getNonProxyImplementationClassIfNecessary(entity.getClass());
        if (entityClass.getAnnotation(Entity.class) != null) {
            Field idField = getIdField(entityClass, session);
            try {
                return (Serializable) idField.get(entity);
            } catch (IllegalAccessException e) {
                throw ExceptionHelper.refineException(e);
            }
        }
        return null;
    }

    @Override
    public Field getIdField(Class<?> clazz, EntityManager em) {
        clazz = getNonProxyImplementationClassIfNecessary(clazz);
        ClassMetadata metadata = em.unwrap(Session.class).getSessionFactory().getClassMetadata(clazz);
        Field idField = ReflectionUtils.findField(clazz, metadata.getIdentifierPropertyName());
        idField.setAccessible(true);
        return idField;
    }

    @Override
    public Field getIdField(Class<?> clazz, Session session) {
        clazz = getNonProxyImplementationClassIfNecessary(clazz);
        ClassMetadata metadata = session.getSessionFactory().getClassMetadata(clazz);
        Field idField = ReflectionUtils.findField(clazz, metadata.getIdentifierPropertyName());
        idField.setAccessible(true);
        return idField;
    }
}
