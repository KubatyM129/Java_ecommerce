package pl.kubaty.ecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import pl.kubaty.ecommerce.catalog.ArrayListProductStorage;
import pl.kubaty.ecommerce.catalog.ProductCatalog;
import pl.kubaty.ecommerce.payu.PayU;
import pl.kubaty.ecommerce.payu.PayUCredentials;
import pl.kubaty.ecommerce.sales.SalesFacade;
import pl.kubaty.ecommerce.sales.cart.InMemoryCartStorage;
import pl.kubaty.ecommerce.sales.offer.OfferCalculator;
import pl.kubaty.ecommerce.sales.payment.PayUGateway;
import pl.kubaty.ecommerce.sales.productdetails.ProductCatalogProductDetailsProvider;
import pl.kubaty.ecommerce.sales.productdetails.ProductDetailsProvider;
import pl.kubaty.ecommerce.sales.reservation.ReservationRepository;

import java.math.BigDecimal;

@SpringBootApplication
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Bean
    ProductCatalog createMyProductCatalog() {
        ProductCatalog productCatalog = new ProductCatalog(new ArrayListProductStorage());
        productCatalog.addProduct("Product 1", "This is the 1st product", BigDecimal.valueOf(10));
        productCatalog.addProduct("Product 2", "This is the 2nd product", BigDecimal.valueOf(20));
        productCatalog.addProduct("Product 3", "This is the 3rd product", BigDecimal.valueOf(50));
        return productCatalog;
    }
    @Bean
    PayU createSandboxPayU() {
        return new PayU(
                new RestTemplate(),
                PayUCredentials.sandbox(
                        "300746",
                        "2ee86a66e5d97e3fadc400c9f19b065d"
                )
        );
    }

    @Bean
    SalesFacade createSales(ProductDetailsProvider productDetailsProvider) {
        return new SalesFacade(
                new InMemoryCartStorage(),
                new OfferCalculator(productDetailsProvider),
                new PayUGateway(createSandboxPayU()),
                new ReservationRepository()
        );
    }

    @Bean
    ProductDetailsProvider createProductDetailsProvider(ProductCatalog catalog) {
        return new ProductCatalogProductDetailsProvider(catalog);
    }

}
