/*
 * #%L
 * BroadleafCommerce Catalog
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

package org.broadleafcommerce.core.search.domain;

import org.apache.commons.collections.CollectionUtils;
import org.apache.solr.common.SolrDocument;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Temporary interface to code to for things that come back from searching. This should be enhanced later to not be dependent on a SolrDocument
 * 
 * @author Phillip Verheyden (phillipuniverse)
 */
public class SearchResultItem implements Map<String, Object>, Iterable<Map.Entry<String, Object>>, Serializable {

    private static final long serialVersionUID = 1L;
    protected SolrDocument doc;
    protected List<SearchResultItem> children;

    public SearchResultItem(SolrDocument doc) {
        this.doc = doc;
    }

    /**
     * @return a list of field names defined in this document - this Collection is directly backed by this SolrDocument.
     * @see #keySet
     */
    public Collection<String> getFieldNames() {
        return doc.getFieldNames();
    }

    ///////////////////////////////////////////////////////////////////
    // Add / Set / Remove Fields
    ///////////////////////////////////////////////////////////////////

    /**
     * Remove all fields from the document
     */
    @Override
    public void clear() {
        doc.clear();
    }

    /**
     * Remove all fields with the name
     */
    public boolean removeFields(String name) {
        return doc.removeFields(name);
    }

    /**
     * Set a field with the given object.  If the object is an Array, it will 
     * set multiple fields with the included contents.  This will replace any existing 
     * field with the given name
     */
    @SuppressWarnings("unchecked")
    public void setField(String name, Object value) {
        doc.setField(name, value);
    }

    /**
     * This will add a field to the document.  If fields already exist with this
     * name it will append value to the collection. If the value is Collection,
     * each value will be added independently. 
     * 
     * The class type of value and the name parameter should match schema.xml. 
     * schema.xml can be found in conf directory under the solr home by default.
     * 
     * @param name Name of the field, should match one of the field names defined under "fields" tag in schema.xml.
     * @param value Value of the field, should be of same class type as defined by "type" attribute of the corresponding field in schema.xml. 
     */
    @SuppressWarnings("unchecked")
    public void addField(String name, Object value) {
        doc.addField(name, value);
    }

    ///////////////////////////////////////////////////////////////////
    // Get the field values
    ///////////////////////////////////////////////////////////////////

    /**
     * returns the first value for a field
     */
    public Object getFirstValue(String name) {
        return doc.getFirstValue(name);
    }

    /**
     * Get the value or collection of values for a given field.  
     */
    public Object getFieldValue(String name) {
        return doc.getFieldValue(name);
    }

    /**
     * Get a collection of values for a given field name
     */
    @SuppressWarnings("unchecked")
    public Collection<Object> getFieldValues(String name) {
        return doc.getFieldValues(name);
    }

    @Override
    public String toString() {
        return doc.toString();
    }

    /**
     * Iterate of String-&gt;Object keys
     */
    @Override
    public Iterator<Entry<String, Object>> iterator() {
        return doc.iterator();
    }

    //-----------------------------------------------------------------------------------------
    // JSTL Helpers
    //-----------------------------------------------------------------------------------------

    /**
     * Expose a Map interface to the solr field value collection.
     */
    public Map<String, Collection<Object>> getFieldValuesMap() {
        return doc.getFieldValuesMap();
    }

    /**
     * Expose a Map interface to the solr fields.  This function is useful for JSTL
     */
    public Map<String, Object> getFieldValueMap() {
        return doc.getFieldValueMap();
    }

    //---------------------------------------------------
    // MAP interface
    //---------------------------------------------------

    @Override
    public boolean containsKey(Object key) {
        return doc.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return doc.containsValue(value);
    }

    @Override
    public Set<Entry<String, Object>> entrySet() {
        return doc.entrySet();
    }

    //TODO: Shouldn't the input parameter here be a String?  The _fields map requires a String.
    @Override
    public Object get(Object key) {
        return doc.get(key);
    }

    @Override
    public boolean isEmpty() {
        return doc.isEmpty();
    }

    @Override
    public Set<String> keySet() {
        return doc.keySet();
    }

    @Override
    public Object put(String key, Object value) {
        return doc.put(key, value);
    }

    @Override
    public void putAll(Map<? extends String, ? extends Object> t) {
        doc.putAll(t);
    }

    @Override
    public Object remove(Object key) {
        return doc.remove(key);
    }

    @Override
    public int size() {
        return doc.size();
    }

    @Override
    public Collection<Object> values() {
        return doc.values();
    }
    
    public SolrDocument getUnderlyingDocument() {
        return doc;
    }

    public void addChildDocument(SearchResultItem child) {
        if (children == null) {
            children = new ArrayList<>();
        }
        children.add(child);
        doc.addChildDocument(child.getUnderlyingDocument());
    }

    public void addChildDocuments(Collection<SearchResultItem> childs) {
        for (SearchResultItem child : childs) {
            addChildDocument(child);
        }
    }

    /** Returns the list of child documents, or null if none. */
    public List<SearchResultItem> getChildren() {
        return children;
    }

    public boolean hasChildDocuments() {
        return CollectionUtils.isNotEmpty(children);
    }

    public int getChildDocumentCount() {
        return children.size();
    }

}
