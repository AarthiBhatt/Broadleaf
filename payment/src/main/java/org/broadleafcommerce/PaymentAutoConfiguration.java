package org.broadleafcommerce;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 * Created by brandon on 12/5/16.
 */
@Configuration
@ImportResource({
        "classpath:/bl-payment-applicationContext.xml"
})
public class PaymentAutoConfiguration {
}
