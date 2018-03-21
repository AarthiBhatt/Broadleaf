/*
 * #%L
 * BroadleafCommerce Open Admin Platform
 * %%
 * Copyright (C) 2009 - 2018 Broadleaf Commerce
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
package org.broadleafcommerce.openadmin.dto;

import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class PropertyDictionary {
    private static final int DEFAULT_INITIAL_CAPACITY = 50;
    private List<Property> list;
    private Map<String, Property> map;
    private Set<String> removed;

    public PropertyDictionary() {
        this(DEFAULT_INITIAL_CAPACITY);
    }

    public PropertyDictionary(int initialCapacity) {
        this.list = new ArrayList<>(initialCapacity);
        this.map = new HashMap<>(initialCapacity);
        this.removed = new HashSet<>(Math.max(1, DEFAULT_INITIAL_CAPACITY / 5));
    }

    public Property get(String name) {
        return map.get(name);
    }

    public void add(Property property) {
        Assert.notNull(property, "Property required");
        map.put(property.getName(), property);
        list.add(property);
    }

    public void addAll(Property[] properties) {
        list.addAll(Arrays.asList(properties));
        for (Property p : properties) {
            map.put(p.getName(), p);
        }
    }

    public Property remove(String name) {
        Property p = map.remove(name);
        if (p != null) {
            removed.add(name);
        }
        return p;
    }

    public void merge(String prefix, Entity entity) {
        prefix = (prefix != null) ? prefix + "." : "";
        Property[] properties = entity.getProperties();
        for (Property prop : properties) {
            String name = prefix + prop.getName();
            prop.setName(name);
            map.put(name, prop);
        }
        list.addAll(Arrays.asList(properties));
    }

    public void override(Entity entity) {
        for (Property newProp : entity.getProperties()) {
            Property prop = map.get(newProp.getName());
            if (prop != null) {
                prop.setValue(newProp.getValue());
                prop.setRawValue(newProp.getRawValue());
                prop.setDisplayValue(newProp.getDisplayValue());
            }
        }

    }

    public int size() {
        return map.size();
    }

    public Property[] toArray() {
        if (!removed.isEmpty()) {
            List<Property> newPropertyList = new ArrayList<>(list.size() - removed.size());
            for (Property p : list) {
                if (!removed.contains(p.getName())) {
                    newPropertyList.add(p);
                }
            }
            list = newPropertyList;
            removed = new HashSet<>();
        }
        return list.toArray(new Property[list.size()]);
    }

    public Map<String, Property> toMap() {
        return map;
    }
}
