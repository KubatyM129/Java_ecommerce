package pl.kubaty.ecommerce.sales.payment;

import pl.kubaty.ecommerce.payu.OrderCreateRequest;

public interface PaymentGateway {
    PaymentDetails registerPayment(RegisterPaymentRequest registerPaymentRequest);

    PaymentDetails registerPayment(OrderCreateRequest registerPaymentRequest);
}
