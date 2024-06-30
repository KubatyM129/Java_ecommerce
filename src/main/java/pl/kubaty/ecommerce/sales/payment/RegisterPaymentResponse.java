package pl.kubaty.ecommerce.sales.payment;

public class RegisterPaymentResponse {
    private final String paymentId;
    private final String paymentUrl;

    public RegisterPaymentResponse(String paymentId, String paymentUrl) {
        this.paymentId = paymentId;
        this.paymentUrl = paymentUrl;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public String getPaymentUrl() {
        return paymentUrl;
    }
}
