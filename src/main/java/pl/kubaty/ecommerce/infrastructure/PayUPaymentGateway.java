package pl.kubaty.ecommerce.infrastructure;

import pl.kubaty.ecommerce.payu.OrderCreateRequest;
import pl.kubaty.ecommerce.sales.payment.PaymentDetails;
import pl.kubaty.ecommerce.sales.payment.PaymentGateway;
import pl.kubaty.ecommerce.sales.payment.RegisterPaymentRequest;

public class PayUPaymentGateway implements PaymentGateway {
    @Override
    public PaymentDetails registerPayment(RegisterPaymentRequest registerPaymentRequest) {
        return null;
    }

    @Override
    public PaymentDetails registerPayment(OrderCreateRequest registerPaymentRequest) {
        return null;
    }
}
