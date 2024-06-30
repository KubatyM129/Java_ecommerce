package pl.kubaty.ecommerce.sales.payment;

import pl.kubaty.ecommerce.payu.OrderCreateRequest;

import java.util.UUID;

public class FakePaymentGateway implements PaymentGateway{
    @Override
    public PaymentDetails registerPayment(OrderCreateRequest registerPaymentRequest) {
        return null;
    }

    @Override
    public PaymentDetails registerPayment(RegisterPaymentRequest registerPaymentRequest) {
        RegisterPaymentResponse response = new RegisterPaymentResponse(
                UUID.randomUUID().toString(),
                "https://www.kek.pl/"
        );

        return new PaymentDetails(
                response.getPaymentUrl(),
                registerPaymentRequest.getReservationId(),
                response.getPaymentId()
                );
    }
}
