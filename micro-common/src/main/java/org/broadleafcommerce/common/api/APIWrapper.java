package org.broadleafcommerce.common.api;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

/**
 * This interface is the super interface for all classes that will provide a JAXB wrapper
 * around classes.  Any class that will be exposed via JAXB annotations to the JAXRS API
 * may implement this as a convenience to provide a standard method to populate data objects.
 *
 * This is not a requirement as objects will not generally be passed using a reference to this
 * interface.
 * @param <T>
 */
public interface APIWrapper<T> extends Serializable {

    public void wrapDetails(T model, HttpServletRequest request);

    public void wrapSummary(T model, HttpServletRequest request);

}

