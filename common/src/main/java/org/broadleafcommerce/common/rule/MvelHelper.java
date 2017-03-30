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
package org.broadleafcommerce.common.rule;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.broadleafcommerce.common.RequestDTO;
import org.broadleafcommerce.common.TimeDTO;
import org.broadleafcommerce.common.logging.RequestLoggingUtil;
import org.broadleafcommerce.common.presentation.client.SupportedFieldType;
import org.broadleafcommerce.common.time.SystemTime;
import org.broadleafcommerce.common.util.BLCSystemProperty;
import org.broadleafcommerce.common.util.EfficientLRUMap;
import org.broadleafcommerce.common.util.FormatUtil;
import org.broadleafcommerce.common.util.StringUtil;
import org.broadleafcommerce.common.web.BroadleafRequestContext;
import org.mvel2.MVEL;
import org.mvel2.ParserContext;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

/**
 * Helper class for some common rule functions that can be called from mvel as well as utility functions
 * to make calling MVEL rules within Broadleaf easier.  
 * 
 * An instance of this class is available to the mvel runtime under the variable name MvelHelper with the 
 * following functions:
 * 
 *    convertField(type, fieldValue)
 *    toUpperCase(value)
 *
 * @author Jeff Fischer
 */
public class MvelHelper {

    private static final Map<String, Serializable> DEFAULT_EXPRESSION_CACHE = new EfficientLRUMap<String, Serializable>(5000);

    private static final Log LOG = LogFactory.getLog(MvelHelper.class);
    public static final String BOGO_SALE_RULE = "CollectionUtils.intersection(orderItem.?sku.?getMultiValueSkuAttributes()[\"promotion-assortments\"],[\"Bogo Sale\"]).size()>0";

    private static boolean TEST_MODE = false;
    
    public static final String BLC_RULE_MAP_PARAM = "blRuleMap";
    
    public static long FIVE_MINUTES = 5*60*1000;
    
    public static long lastCheckExpressionCacheTimeStamp=0;     
    public static boolean disabledMvelExpressionCache=false;
    
    public static long lastCheckRemoveCachedMvelRuleTimeStamp=0;
    public static boolean removeCachedMvelRule=false;


    /**
     * This method is potentially expensive so we want to only check to see if the property has been 
     * updated at most once per minute
     * @return
     */
    private static boolean getDisabledMvelExpressionCache() {
        long currentTime = SystemTime.asMillis();
        if (lastCheckExpressionCacheTimeStamp < (currentTime - FIVE_MINUTES)) {
            lastCheckExpressionCacheTimeStamp = currentTime;
            disabledMvelExpressionCache = BLCSystemProperty.resolveBooleanSystemProperty("disable.mvel.expression.cache", false);
        }
        return disabledMvelExpressionCache;
    }

    /**
     * This method is potentially expensive so we want to only check to see if the property has been 
     * updated at most once per minute
     * @return
     */
    private static boolean getRemoveCachedMvelRule() {
        long currentTime = SystemTime.asMillis();
        if (lastCheckRemoveCachedMvelRuleTimeStamp < (currentTime - FIVE_MINUTES)) {
            lastCheckRemoveCachedMvelRuleTimeStamp = currentTime;
            removeCachedMvelRule = BLCSystemProperty.resolveBooleanSystemProperty("remove.mvel.rule.on.exception", false);
        }
        return removeCachedMvelRule;
    }

    // The following attribute is set in BroadleafProcessURLFilter
    public static final String REQUEST_DTO = "blRequestDTO";    
    
    /**
     * Converts a field to the specified type.    Useful when 
     * @param type
     * @param fieldValue
     * @return
     */
    public static Object convertField(String type, String fieldValue) {
        if (fieldValue == null) {
            return null;
        }
        try {
            if (type.equals(SupportedFieldType.BOOLEAN.toString())) {
                return Boolean.parseBoolean(fieldValue);
            } else if (type.equals(SupportedFieldType.DATE.toString())) {
                return FormatUtil.getTimeZoneFormat().parse(fieldValue);
            } else if (type.equals(SupportedFieldType.INTEGER.toString())) {
                return Integer.parseInt(fieldValue);
            } else if (type.equals(SupportedFieldType.MONEY.toString()) || type.equals(SupportedFieldType.DECIMAL.toString())) {
                return new BigDecimal(fieldValue);
            }
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        throw new IllegalArgumentException("Unrecognized type(" + type + ") for map field conversion.");
    }

    public static Object toUpperCase(String value) {
        if (value == null) {
            return null;
        }
        return value.toUpperCase();
    }
    
    /**
     * Returns true if the passed in rule passes based on the passed in ruleParameters.   
     * 
     * Also returns true if the rule is blank or null.
     * 
     * Calls the {@link #evaluateRule(String, Map, Map)} method passing in the DEFAULT_EXPRESSION_CACHE.
     * For systems that need to cache a large number of rule expressions, an alternate cache can be passed in.   The
     * default cache is able to cache up to 1,000 rule expressions which should suffice for most systems.
     * 
     * @param rule
     * @param ruleParameters
     * @return
     */
    public static boolean evaluateRule(String rule, Map<String, Object> ruleParameters) {
        return evaluateRule(rule, ruleParameters, DEFAULT_EXPRESSION_CACHE);
    }

    /**
     * Evaluates the passed in rule given the passed in parameters.   
     *
     * @param rule
     * @param ruleParameters
     * @return
     */
    public static boolean evaluateRule(String rule, Map<String, Object> ruleParameters,
            Map<String, Serializable> expressionCache) {
        return evaluateRule(rule, ruleParameters, expressionCache, null);
    }

    /**
     * @param rule
     * @param ruleParameters
     * @param expressionCache
     * @param additionalContextImports additional imports to give to the {@link ParserContext} besides "MVEL" ({@link MVEL} and
     * "MvelHelper" ({@link MvelHelper}) since they are automatically added 
     * @return
     */
    public static boolean evaluateRule(String rule, Map<String, Object> ruleParameters,
            Map<String, Serializable> expressionCache, Map<String, Class<?>> additionalContextImports) {
        if (getDisabledMvelExpressionCache()) {
            return MvelHelper.evaluateRuleWithoutCache(rule, ruleParameters, additionalContextImports);
        }

        boolean ruleIsBogoSaleRule = false;
        if (rule != null) {
            ruleIsBogoSaleRule = rule.equals(BOGO_SALE_RULE);
        }
        if (LOG.isInfoEnabled() && RequestLoggingUtil.isRequestLoggingEnabled() && ruleIsBogoSaleRule) {
            boolean mvelDisableJit = Boolean.getBoolean("mvel2.disable.jit");
            RequestLoggingUtil.logInfoRequestMessage("MvelHelper Logging : Property mvel2.disable.jit value: " + mvelDisableJit, RequestLoggingUtil.BL_OFFER_LOG);
        }

        // Null or empty is a match
        if (rule == null || "".equals(rule)) {
            return true;
        } else {
            Serializable exp = null;
            if (expressionCache != null) {
                // MVEL expression compiling can be expensive so let's cache the expression
                exp = expressionCache.get(rule);
            }
            boolean fromCache = true;
            if (exp == null) {
                fromCache = false;
                ParserContext context = new ParserContext();
                context.addImport("MVEL", MVEL.class);
                context.addImport("MvelHelper", MvelHelper.class);
                context.addImport("CollectionUtils", SelectizeCollectionUtils.class);
                if (MapUtils.isNotEmpty(additionalContextImports)) {
                    for (Entry<String, Class<?>> entry : additionalContextImports.entrySet()) {
                        context.addImport(entry.getKey(), entry.getValue());
                    }
                }
                
                String modifiedRule = modifyExpression(rule, ruleParameters, context);

                synchronized (expressionCache) {
                    exp = MVEL.compileExpression(modifiedRule, context);
                    expressionCache.put(rule, exp);
                }


            }

            Map<String, Object> mvelParameters = new HashMap<String, Object>();

            if (ruleParameters != null) {
                for (String parameter : ruleParameters.keySet()) {
                    mvelParameters.put(parameter, ruleParameters.get(parameter));
                }
            }

            try {
                Object test = MVEL.executeExpression(exp, mvelParameters);
                if (test == null) {
                    // This can occur if there is no actual rule
                    return true;
                }

                boolean result = (Boolean) test;

                if (LOG.isInfoEnabled() && RequestLoggingUtil.isRequestLoggingEnabled() && ruleIsBogoSaleRule) {
                    RequestLoggingUtil.logInfoRequestMessage("MvelHelper Logging : Result of executing Mvel expression: result=" + result, RequestLoggingUtil.BL_OFFER_LOG);
                    try {
                        result = smartLogging(mvelParameters, result);
                    } catch (Exception e) {
                        RequestLoggingUtil.logInfoRequestMessage("MvelHelper Logging ERROR : Caught exception from smart logging. Returning result=" + result, RequestLoggingUtil.BL_OFFER_LOG);
                    }
                }
                return result;
            } catch (Exception e) {
                if (LOG.isInfoEnabled() && RequestLoggingUtil.isRequestLoggingEnabled() && ruleIsBogoSaleRule) {
                    RequestLoggingUtil.logInfoRequestMessage("MvelHelper Logging : Unable to parse and/or execute the mvel expression (" +
                            rule + "). Reporting to the logs and returning false for the match expression", RequestLoggingUtil.BL_OFFER_LOG);
                    RequestLoggingUtil.logInfoRequestMessage("MvelHelper Logging : Reason for expression failure: " + ExceptionUtils.getStackTrace(e), RequestLoggingUtil.BL_OFFER_LOG);
                }

                if (getRemoveCachedMvelRule()) {
                    // Just in case, let's remove this rule.
                    if (expressionCache != null && rule != null && expressionCache.containsKey(rule)) {
                        synchronized (expressionCache) {
                            expressionCache.remove(rule);
                        }
                        if (LOG.isInfoEnabled()) {
                            LOG.info("Removed rule " + StringUtil.sanitize(rule) + " from expression cache.");
                        }
                    }
                }

                return false;
            }
        }
    }

    public static boolean smartLogging(Map<String, Object> mvelParameters, boolean result) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Class oiClass;
        try {
            oiClass = Class.forName("org.broadleafcommerce.core.order.domain.OrderItem");
        } catch (Exception e) {
            RequestLoggingUtil.logInfoRequestMessage("MvelHelper Logging ERROR : " + e.getClass() + 
                    " when attempting to get OrderItem class. Returning result=" + result, RequestLoggingUtil.BL_OFFER_LOG);
            return result;
        }

        if (MapUtils.isEmpty(mvelParameters)) {
            RequestLoggingUtil.logInfoRequestMessage("MvelHelper Logging ERROR : mvelParameters is empty or null." +
                    " Returning result=" + result, RequestLoggingUtil.BL_OFFER_LOG);
            return result;
        }
        if (mvelParameters.get("orderItem") == null) {
            RequestLoggingUtil.logInfoRequestMessage("MvelHelper Logging ERROR : orderItem on mvelParameters is null." +
                    " Returning result=" + result, RequestLoggingUtil.BL_OFFER_LOG);
            return result;
        }

        Object orderItem;
        try {
            orderItem = oiClass.cast(mvelParameters.get("orderItem"));
        } catch (Exception e) {
            RequestLoggingUtil.logInfoRequestMessage("MvelHelper Logging ERROR : orderItem from mvelParameters threw a " + e.getClass() +
                    " when attempting to cast to OrderItem. Returning result=" + result, RequestLoggingUtil.BL_OFFER_LOG);
            return result;
        }

        RequestLoggingUtil.logInfoRequestMessage("MvelHelper Logging : orderItem is an instance of " + 
                orderItem.getClass(), RequestLoggingUtil.BL_OFFER_LOG);

        try {
            Method oiGetIdMethod = oiClass.getDeclaredMethod("getId", new Class[]{});
            Object oiId = oiGetIdMethod.invoke(orderItem, new Class[]{});

            RequestLoggingUtil.logInfoRequestMessage("MvelHelper Logging : the id of the orderItem is " +
                    oiId, RequestLoggingUtil.BL_OFFER_LOG);
        } catch (Exception e) {
            RequestLoggingUtil.logInfoRequestMessage("MvelHelper Logging ERROR : orderItem threw a " + e.getClass() +
                    " when attempting to retrieve id. Continuing.", RequestLoggingUtil.BL_OFFER_LOG);
        }

        try {
            orderItem = orderItem.getClass().cast(orderItem);
        } catch (Exception e) {
            RequestLoggingUtil.logInfoRequestMessage("MvelHelper Logging ERROR : threw a " + e.getClass() +
                    " when attempting to cast orderItem to " + orderItem.getClass() + ". Returning result=" + result, RequestLoggingUtil.BL_OFFER_LOG);
            return result;
        }

        Object sku;
        try {
            Method oiGetSkuMethod = orderItem.getClass().getDeclaredMethod("getSku", new Class[]{});
            sku = oiGetSkuMethod.invoke(orderItem, new Class[]{});

        } catch (Exception e) {
            RequestLoggingUtil.logInfoRequestMessage("MvelHelper Logging ERROR : threw a " + e.getClass() +
                    " when attempting to get the sku from the orderItem. Returning result=" + result, RequestLoggingUtil.BL_OFFER_LOG);
            return result;
        }

        RequestLoggingUtil.logInfoRequestMessage("MvelHelper Logging : sku is an instance of " +
                sku.getClass(), RequestLoggingUtil.BL_OFFER_LOG);

        try {
            Method skuGetIdMethod = sku.getClass().getDeclaredMethod("getId", new Class[]{});
            Object skuId = skuGetIdMethod.invoke(sku, new Class[]{});

            RequestLoggingUtil.logInfoRequestMessage("MvelHelper Logging : the id of the sku is " +
                    skuId, RequestLoggingUtil.BL_OFFER_LOG);
        } catch (Exception e) {
            RequestLoggingUtil.logInfoRequestMessage("MvelHelper Logging ERROR : sku threw a " + e.getClass() +
                    " when attempting to retrieve id. Continuing.", RequestLoggingUtil.BL_OFFER_LOG);
        }

        Map<String, Object> skuAttributeMap;
        try {
            Method skuGetAttributesMethod = sku.getClass().getDeclaredMethod("getMultiValueSkuAttributes", new Class[]{});
            skuAttributeMap = (Map<String, Object>) skuGetAttributesMethod.invoke(sku, new Class[]{});
        } catch (Exception e) {
            RequestLoggingUtil.logInfoRequestMessage("MvelHelper Logging ERROR : threw a " + e.getClass() +
                    " when attempting to get the multiValueAttributes from the sku. Returning result=" + result, RequestLoggingUtil.BL_OFFER_LOG);
            return result;
        }

        if (MapUtils.isEmpty(skuAttributeMap)) {
            RequestLoggingUtil.logInfoRequestMessage("MvelHelper Logging ERROR : skuAttributeMap is empty or null." +
                    " Returning result=" + result, RequestLoggingUtil.BL_OFFER_LOG);
            return result;
        }

        RequestLoggingUtil.logInfoRequestMessage("MvelHelper Logging : skuAttributeMap is size " +
                skuAttributeMap.size(), RequestLoggingUtil.BL_OFFER_LOG);

        try {
            for (String skuAttributeKey : skuAttributeMap.keySet()) {

                Object skuAttributeList = skuAttributeMap.get(skuAttributeKey);
                
                for (Object skuAttribute : ((ArrayList)skuAttributeList)) {


                    Method skuAttributeGetIdMethod = skuAttribute.getClass().getDeclaredMethod("getId", new Class[]{});
                    Object skuAttributeId = skuAttributeGetIdMethod.invoke(skuAttribute, new Class[]{});

                    Method skuAttributeGetValueMethod = skuAttribute.getClass().getDeclaredMethod("getValue", new Class[]{});
                    Object skuAttributeValue = skuAttributeGetValueMethod.invoke(skuAttribute, new Class[]{});

                    RequestLoggingUtil.logInfoRequestMessage("MvelHelper Logging : skuAttributeMap contains id=" + skuAttributeId +
                            ", key=" + skuAttributeKey + ", value=" + skuAttributeValue, RequestLoggingUtil.BL_OFFER_LOG);
                }
            }
        } catch (Exception e) {
            RequestLoggingUtil.logInfoRequestMessage("MvelHelper Logging ERROR : when attempting to get sku attributes map, " +
                    "encountered " + e.getClass() + ". Continuing", RequestLoggingUtil.BL_OFFER_LOG);
        }

        if (skuAttributeMap.get("promotion-assortments") == null) {
            RequestLoggingUtil.logInfoRequestMessage("MvelHelper Logging ERROR : promotion-assortments on skuAttributeMap is null." +
                    " Returning result=" + result, RequestLoggingUtil.BL_OFFER_LOG);
            return result;
        }

        List<Object> promoAssortments = (List<Object>) skuAttributeMap.get("promotion-assortments");

        RequestLoggingUtil.logInfoRequestMessage("MvelHelper Logging : Promotion assortments on this sku is of size: " + promoAssortments.size(), RequestLoggingUtil.BL_OFFER_LOG);

        if (result) {
            ArrayList<String> bogoSaleList = new ArrayList<String>();
            bogoSaleList.add("Bogo Sale");

            result = SelectizeCollectionUtils.intersection(promoAssortments, bogoSaleList).size() > 0;
            
            

            RequestLoggingUtil.logInfoRequestMessage("MvelHelper Logging : Result of re-evaluating Mvel expression using Java, result=" + result, RequestLoggingUtil.BL_OFFER_LOG);
        }
        
        return result;
    }

    /**
     * @param rule
     * @param ruleParameters
     * @param additionalContextImports additional imports to give to the {@link ParserContext} besides "MVEL" ({@link MVEL} and
     * "MvelHelper" ({@link MvelHelper}) since they are automatically added 
     * @return
     */
    public static boolean evaluateRuleWithoutCache(String rule, Map<String, Object> ruleParameters,
                                                   Map<String, Class<?>> additionalContextImports) {

        // Null or empty is a match
        if (rule == null || "".equals(rule)) {
            return true;
        } else {
            ParserContext context = new ParserContext();
            context.addImport("MVEL", MVEL.class);
            context.addImport("MvelHelper", MvelHelper.class);
            context.addImport("CollectionUtils", SelectizeCollectionUtils.class);
            if (MapUtils.isNotEmpty(additionalContextImports)) {
                for (Entry<String, Class<?>> entry : additionalContextImports.entrySet()) {
                    context.addImport(entry.getKey(), entry.getValue());
                }
            }

            rule = modifyExpression(rule, ruleParameters, context);
            Serializable exp = MVEL.compileExpression(rule, context);

            Map<String, Object> mvelParameters = new HashMap<String, Object>();

            if (ruleParameters != null) {
                for (String parameter : ruleParameters.keySet()) {
                    mvelParameters.put(parameter, ruleParameters.get(parameter));
                }
            }

            try {
                Object test = MVEL.executeExpression(exp, mvelParameters);
                if (test == null) {
                    // This can occur if there is no actual rule
                    return true;
                }

                return (Boolean) test;
            } catch (Exception e) {
                RequestLoggingUtil.logDebugRequestMessage("Unable to parse and/or execute the non-cached mvel expression (" +
                        rule + "). Reporting to the logs and returning false for the match expression", RequestLoggingUtil.BL_OFFER_LOG);
                RequestLoggingUtil.logDebugRequestMessage("Reason for expression failure: " + ExceptionUtils.getStackTrace(e), RequestLoggingUtil.BL_OFFER_LOG);

                //Unable to execute the MVEL expression for some reason
                //Return false, but notify about the bad expression through logs
                if (!TEST_MODE) {
                    LOG.info("Unable to parse and/or execute the non-cached mvel expression (" + rule + "). " +
                            "Reporting to the logs and returning false for the match expression", e);
                }

                return false;
            }
        }
    }
    
    /**
     * <p>
     * Provides a hook point to modify the final expression before it's built. By default, this looks for attribute
     * maps and replaces them such that it does string comparison.
     * 
     * <p>
     * For example, given an expression like getProductAttributes()['somekey'] == 'someval', getProductAttributes()['somekey']
     * actually returns a ProductAttribute object, not a String, so the comparison is wrong. Instead, we actually want
     * to do this: getProductAttributes().?get('somekey').?value == 'someval'. This function performs that replacement
     *
     * <p>
     * The modification regex will support both simple and complex expressions like:
     * "(MvelHelper.convertField("INTEGER",orderItem.?product.?getProductAttributes()["myinteger"])>0&&MvelHelper.convertField("INTEGER",orderItem.?product.?getProductAttributes()["myinteger"])<10)"
     *
     * @param rule the rule to replace
     * @return a modified version of <b>rule</b>
     * @see {@link #getRuleAttributeMaps()}
     */
    protected static String modifyExpression(String rule, Map<String, Object> ruleParameters, ParserContext context) {
        String modifiedExpression = rule;
        for (String attributeMap : getRuleAttributeMaps()) {
            modifiedExpression = modifiedExpression.replaceAll(attributeMap + "\\(\\)\\[(.*?)\\](?!\\.\\?value)", attributeMap + "().?get($1).?value");
        }
        return modifiedExpression;
    }

    /**
     * Returns an array of attribute map field names that we need to do replacements for in
     * {@link #modifyExpression(String, Map, ParserContext)}
     */
    protected static String[] getRuleAttributeMaps() {
        // intentionally left out pricing context getPricingContextAttributes because that's a Map<String, String>
        return new String[]{ "getProductAttributes",
            "getCategoryAttributesMap",
            "getSkuAttributes",
            "getOrderItemAttributes",
            "getCustomerAttributes",
            // Map<String, PageAttribute>
            "getAdditionalAttributes",
            // Map<String, AdminUserAttribute>
            "getAdditionalFields"}; 
    }

    /**
     * When true, LOG.info statement will be suppressed.   Should only be set from within MvelHelperTest.
     * Prevents an error from displaying during unit test runs.
     * @param testMode
     */
    public static void setTestMode(boolean testMode) {
        TEST_MODE = testMode;
    }
    
    /**
     * Builds parameters using time, request, customer, and cart.
     * 
     * Should be called from within a valid web request.
     *
     * @return
     */
    public static Map<String, Object> buildMvelParameters() {
        Map<String, Object> mvelParameters = new HashMap<String, Object>();
       BroadleafRequestContext brc = BroadleafRequestContext.getBroadleafRequestContext();
        if (brc != null && brc.getRequest() != null) {
           TimeDTO timeDto = new TimeDTO(SystemTime.asCalendar());
            HttpServletRequest request = brc.getRequest();
            RequestDTO requestDto = brc.getRequestDTO();
            mvelParameters.put("time", timeDto);
            mvelParameters.put("request", requestDto);

            Map<String, Object> blcRuleMap = (Map<String, Object>) request.getAttribute(BLC_RULE_MAP_PARAM);
            if (blcRuleMap != null) {
                for (String mapKey : blcRuleMap.keySet()) {
                    mvelParameters.put(mapKey, blcRuleMap.get(mapKey));
                }
           }
       }

       return mvelParameters;
    }

    public static void clearExpressionCache() {
        DEFAULT_EXPRESSION_CACHE.clear();
    }
}
