package pl.kubaty.ecommerce.sales.reservation;

import pl.kubaty.ecommerce.payu.OrderCreateRequest;
import pl.kubaty.ecommerce.sales.payment.PaymentDetails;
import pl.kubaty.ecommerce.sales.payment.PaymentGateway;
import pl.kubaty.ecommerce.sales.payment.RegisterPaymentRequest;

import java.util.UUID;

public class SpyPaymentGateway implements PaymentGateway {
    Integer requestCount = 0;
    public RegisterPaymentRequest lastRequest;
    public Integer getRequestCount() {
        return requestCount;
    }

    @Override
    public PaymentDetails registerPayment(RegisterPaymentRequest registerPaymentRequest) {
        this.requestCount++;
        lastRequest = registerPaymentRequest;
        return new PaymentDetails(
                "http://spy-gateway",
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString());
    }

    @Override
    public PaymentDetails registerPayment(OrderCreateRequest registerPaymentRequest) {
        return null;
    }
}
