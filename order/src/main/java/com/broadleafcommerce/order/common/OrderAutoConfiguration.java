package com.broadleafcommerce.order.common;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 * Created by brandon on 12/9/16.
 */
@Configuration
@ImportResource({
        "classpath:/bl-order-applicationContext-persistence.xml",
        "classpath:/bl-order-applicationContext-entity.xml",
        "classpath:/bl-order-applicationContext.xml",
        "classpath:/bl-order-applicationContext-workflow.xml"
})
public class OrderAutoConfiguration {
}
