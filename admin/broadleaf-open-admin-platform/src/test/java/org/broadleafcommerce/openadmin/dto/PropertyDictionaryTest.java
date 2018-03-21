package org.broadleafcommerce.openadmin.dto;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import org.junit.rules.TestName;

import static org.junit.Assert.*;

public class PropertyDictionaryTest {
    private static final int PROPERTIES_SIZE = 5000;
    private static final String NAME_PREFIX = "prop";
    private final String SEARCH_PROPERTY = NAME_PREFIX + 321;

    private static PropertyDictionary dictionary;
    private static PropertyDictionary dictionaryWithProperties;
    private static Property property;
    private static long startTime;
    private static Entity entity;
    private static Entity entityWithProperties;
    private static Property[] properties;

    @Rule
    public TestName testName = new TestName();

    @Before
    public void before() {
        dictionary = new PropertyDictionary();
        property = new Property();
        entity = new Entity();
        entity.setProperties(new Property[0]);
        properties = createProperties(PROPERTIES_SIZE);
        entityWithProperties = new Entity();
        entityWithProperties.setProperties(properties);
        dictionaryWithProperties = new PropertyDictionary();
        dictionaryWithProperties.addAll(properties);
        startTime = System.nanoTime(); // call it last to avoid measure init time
    }

    @After
    public void after() {
        String methodName = testName.getMethodName();
        if (methodName.startsWith("test_time")) {
            long deltaTime = (System.nanoTime() - startTime) / 1_000_000;
            System.out.println(methodName + " : " + deltaTime + " millis");
        }
    }

    private Property[] createProperties(int len) {
        Property[] properties = new Property[len];
        for (int i = 0; i < len; i++) {
            Property p = new Property();
            String val = String.valueOf(i);
            p.setName(NAME_PREFIX + val);
            p.setValue(val);
            properties[i] = p;
        }
        return properties;
    }

    @Test
    public void test_size_after_add() {
        dictionary.add(property);
        int size = dictionary.size();
        assertEquals(1, size);
    }

    @Test
    public void test_size_after_add_remove_add() {
        String pName = property.getName();
        dictionary.add(property);
        dictionary.remove(pName);
        dictionary.add(property);
        int size = dictionary.size();
        assertEquals(1, size);
    }

    @Test
    public void test_property_null_after_add_remove() {
        String pName = property.getName();
        dictionary.add(property);
        dictionary.remove(pName);
        assertEquals(null, dictionary.get(pName));
    }

    @Test
    public void test_property_merge() {
        dictionaryWithProperties.merge("prefix", entityWithProperties);
        assertEquals("1", dictionaryWithProperties.get("prefix.prop1").getValue());
    }

    @Test
    public void test_property_override() {
        Entity e = new Entity();
        Property p = new Property("prop1", "new-val");
        Property[] props = new Property[1];
        props[0] = p;
        e.setProperties(props);
        dictionaryWithProperties.override(e);
        assertEquals("new-val", dictionaryWithProperties.get("prop1").getValue());
    }

    /**
     * TEST TIME
     */

    @Test
    public void test_time_find_new() {
        dictionaryWithProperties.get(SEARCH_PROPERTY);
    }

    @Test
    public void test_time_find_old() {
        entityWithProperties.findProperty(SEARCH_PROPERTY);
    }

    @Test
    public void test_time_add_all_new() {
        for (Property p : properties) {
            dictionary.add(p);
        }
    }

    @Test
    public void test_time_add_all_old() {
        for (Property p : properties) {
            entity.addProperty(p);
        }
    }

    @Test
    public void test_time_remove_all_new() {
        for (Property p : properties) {
            dictionaryWithProperties.remove(p.getName());
        }
    }

    @Test
    public void test_time_remove_all_old() {
        for (Property p : properties) {
            entityWithProperties.removeProperty(p.getName());
        }
    }

    @Test
    public void test_time_override_all_new() {
        dictionaryWithProperties.override(entityWithProperties);
    }

    @Test
    public void test_time_override_all_old() {
        entityWithProperties.overridePropertyValues(entityWithProperties);
    }

    @Test
    public void test_time_merge_new() {
        dictionaryWithProperties.merge("new", entityWithProperties);
    }

    @Test
    public void test_time_merge_old() {
        entityWithProperties.mergeProperties("new", entityWithProperties);
    }

    @Test
    public void test_time_to_array_new() {
        dictionaryWithProperties.toArray();
    }

    @Test
    public void test_time_to_array_old() {
        entityWithProperties.getProperties();
    }

    @Test
    public void test_time_toMap_new() {
        dictionaryWithProperties.toMap();
    }

    @Test
    public void test_time_toMap_old() {
        entityWithProperties.getPMap();
    }
}