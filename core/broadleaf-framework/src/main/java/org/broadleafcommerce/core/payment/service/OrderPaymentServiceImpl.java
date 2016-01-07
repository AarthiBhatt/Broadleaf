/*
 * #%L
 * BroadleafCommerce Framework
 * %%
 * Copyright (C) 2009 - 2013 Broadleaf Commerce
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *       http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package org.broadleafcommerce.core.payment.service;

import org.apache.commons.collections.MapUtils;
import org.broadleafcommerce.common.money.Money;
import org.broadleafcommerce.common.payment.PaymentAdditionalFieldType;
import org.broadleafcommerce.common.payment.PaymentGatewayType;
import org.broadleafcommerce.common.payment.PaymentTransactionType;
import org.broadleafcommerce.common.payment.PaymentType;
import org.broadleafcommerce.common.time.SystemTime;
import org.broadleafcommerce.common.util.TransactionUtils;
import org.broadleafcommerce.core.order.domain.Order;
import org.broadleafcommerce.core.payment.dao.OrderPaymentDao;
import org.broadleafcommerce.core.payment.domain.OrderPayment;
import org.broadleafcommerce.core.payment.domain.PaymentLog;
import org.broadleafcommerce.core.payment.domain.PaymentTransaction;
import org.broadleafcommerce.profile.core.domain.CustomerPayment;
import org.broadleafcommerce.profile.core.service.AddressService;
import org.broadleafcommerce.profile.core.service.CustomerPaymentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

@Service("blOrderPaymentService")
public class OrderPaymentServiceImpl implements OrderPaymentService {

    @Resource(name = "blOrderPaymentDao")
    protected OrderPaymentDao paymentDao;

    @Resource(name = "blCustomerPaymentService")
    protected CustomerPaymentService customerPaymentService;

    @Resource(name = "blAddressService")
    protected AddressService addressService;

    @Override
    @Transactional(value = TransactionUtils.DEFAULT_TRANSACTION_MANAGER)
    public OrderPayment save(OrderPayment payment) {
        return paymentDao.save(payment);
    }

    @Override
    @Transactional(value = TransactionUtils.DEFAULT_TRANSACTION_MANAGER)
    public PaymentTransaction save(PaymentTransaction transaction) {
        return paymentDao.save(transaction);
    }

    @Override
    public PaymentLog save(PaymentLog log) {
        return paymentDao.save(log);
    }

    @Override
    public OrderPayment readPaymentById(Long paymentId) {
        return paymentDao.readPaymentById(paymentId);
    }

    @Override
    public List<OrderPayment> readPaymentsForOrder(Order order) {
        return paymentDao.readPaymentsForOrder(order);
    }

    @Override
    public OrderPayment create() {
        return paymentDao.create();
    }

    @Override
    @Transactional(value = TransactionUtils.DEFAULT_TRANSACTION_MANAGER)
    public void delete(OrderPayment payment) {
        paymentDao.delete(payment);
    }

    @Override
    public PaymentLog createLog() {
        return paymentDao.createLog();
    }

    @Override
    public PaymentTransaction createTransaction() {
        PaymentTransaction returnItem = paymentDao.createTransaction();
        
        //TODO: this needs correct timezone conversion, right?
        returnItem.setDate(SystemTime.asDate());
        
        return returnItem;
    }

    @Override
    public PaymentTransaction readTransactionById(Long transactionId) {
        return paymentDao.readTransactionById(transactionId);
    }

    @Override
    @Transactional(value = TransactionUtils.DEFAULT_TRANSACTION_MANAGER)
    public OrderPayment createOrderPaymentFromCustomerPayment(Order order, CustomerPayment customerPayment, Money amount) {
        OrderPayment orderPayment = create();
        orderPayment.setOrder(order);
        Map<String, String> additionalFields = customerPayment.getAdditionalFields();
        if (MapUtils.isEmpty(additionalFields)) {
            additionalFields = new HashMap<>();
        }
        if (additionalFields.containsKey(PaymentAdditionalFieldType.PAYMENT_TYPE)) {
            String paymentType = additionalFields.get(PaymentAdditionalFieldType.PAYMENT_TYPE);
            PaymentType pType = PaymentType.getInstance(paymentType);
            if (pType != null) {
                orderPayment.setType(pType);
            }
        }
        orderPayment.setBillingAddress(addressService.copyAddress(customerPayment.getBillingAddress()));
        if (additionalFields.containsKey(PaymentAdditionalFieldType.GATEWAY_TYPE)) {
            String gatewayType = additionalFields.get(PaymentAdditionalFieldType.GATEWAY_TYPE);
            PaymentGatewayType gType = PaymentGatewayType.getInstance(gatewayType);
            if (gType != null) {
                orderPayment.setPaymentGatewayType(gType);
            }
        }

        orderPayment.setAmount(amount);

        PaymentTransaction unconfirmedTransaction = createTransaction();
        unconfirmedTransaction.setAmount(amount);
        unconfirmedTransaction.setType(PaymentTransactionType.UNCONFIRMED);
        unconfirmedTransaction.setOrderPayment(orderPayment);
        unconfirmedTransaction.getAdditionalFields().put(PaymentAdditionalFieldType.TOKEN.getType(), customerPayment.getPaymentToken());
        unconfirmedTransaction.getAdditionalFields().putAll(customerPayment.getAdditionalFields());

        orderPayment.getTransactions().add(unconfirmedTransaction);

        return save(orderPayment);
    }

}
