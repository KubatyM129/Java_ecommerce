package pl.kubaty.ecommerce.payu;

import pl.kubaty.ecommerce.sales.offer.AcceptOfferRequest;
import pl.kubaty.ecommerce.sales.offer.Offer;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class OrderCreateRequest {
    String customerIp;
    String notifyUrl;

    String merchantPosId;
    String description;
    String currencyCode;
    String totalAmount;
    String extOrderId;
    Buyer buyer;
    List<Product> products;

    public static OrderCreateRequest of(String reservationId, AcceptOfferRequest acceptOfferRequest, BigDecimal total, Offer products) {
        return new OrderCreateRequest()
                .setNotifyUrl("https://my.example.shop.kubaty.pl/api/order")
                .setCustomerIp("127.0.0.1")
                .setMerchantPosId("300746")
                .setDescription("Payment")
                .setCurrencyCode("PLN")
                .setTotalAmount(total.movePointRight(2).toString())
                .setExtOrderId(reservationId)
                .setBuyer(new Buyer()
                        .setLanguage("pl")
                        .setEmail(acceptOfferRequest.getEmail())
                        .setFirstName(acceptOfferRequest.getFirstName())
                        .setLastName(acceptOfferRequest.getLastName())
                )
                .setProducts(products.getLines().stream()
                        .map(lineItem -> new Product(
                                "Product",
                                lineItem.getPrice().intValue(),
                                lineItem.getQuantity()
                        )
                        )
                        .collect(Collectors.toList())
                );
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public OrderCreateRequest setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
        return this;
    }


    public String getCustomerIp() {
        return customerIp;
    }

    public OrderCreateRequest setCustomerIp(String customerIp) {
        this.customerIp = customerIp;
        return this;
    }

    public String getMerchantPosId() {
        return merchantPosId;
    }

    public OrderCreateRequest setMerchantPosId(String merchantPosId) {
        this.merchantPosId = merchantPosId;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public OrderCreateRequest setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public OrderCreateRequest setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
        return this;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public OrderCreateRequest setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
        return this;
    }

    public String getExtOrderId() {
        return extOrderId;
    }

    public OrderCreateRequest setExtOrderId(String extOrderId) {
        this.extOrderId = extOrderId;
        return this;
    }

    public Buyer getBuyer() {
        return buyer;
    }

    public OrderCreateRequest setBuyer(Buyer buyer) {
        this.buyer = buyer;
        return this;
    }

    public List<Product> getProducts() {
        return products;
    }

    public OrderCreateRequest setProducts(List<Product> products) {
        this.products = products;
        return this;
    }
}



