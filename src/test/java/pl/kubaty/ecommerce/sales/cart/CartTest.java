package pl.kubaty.ecommerce.sales.cart;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
public class CartTest {


    private static final String PRODUCT_1 = "Lego set X" ;
    private static final String PRODUCT_2 = "Lego set Y";

    @Test
    void newlyCreatedCartIsEmpty() {
        Cart cart = Cart.empty();

        assertThat(cart.isEmpty()).isTrue();

    }

    @Test
    void cartWithProductIsNoEmpty() {
        Cart cart = Cart.empty();
        String productId = thereIsProduct(PRODUCT_1);

        cart.addProduct(productId);

        assertThat(cart.isEmpty()).isFalse();
    }

    @Test
    void itExposeProductsCountS1() {
        Cart cart = Cart.empty();
        String productId = thereIsProduct(PRODUCT_1);

        cart.addProduct(productId);

        assertThat(cart.getProductsCount()).isEqualTo(1);
    }

    @Test
    void itExposeProductsCountS2() {
        Cart cart = Cart.empty();
        String productId1 = thereIsProduct(PRODUCT_1);
        String productId2 = thereIsProduct(PRODUCT_2);

        cart.addProduct(productId1);
        cart.addProduct(productId2);

        assertThat(cart.getProductsCount()).isEqualTo(2);
    }

    @Test
    void itExposeProductsCountS3() {
        Cart cart = Cart.empty();
        String productId = thereIsProduct(PRODUCT_1);

        cart.addProduct(productId);
        cart.addProduct(productId);

        assertThat(cart.getProductsCount()).isEqualTo(1);
    }

    @Test
    void itExposeCollectedItems() {
        Cart cart = Cart.empty();
        String productId = thereIsProduct(PRODUCT_1);

        cart.addProduct(productId);

        List<CartLine> lines = cart.getLines();

        assertThat(lines)
                .hasSize(1)
                .extracting("productId")
                .contains(PRODUCT_1);

        assertCartContainsQuantityForProduct(lines, PRODUCT_1, 1);
    }

    private void assertCartContainsQuantityForProduct(List<CartLine> lines, String productId, int expectedQty) {
        assertThat(lines)
                .filteredOn(cartLine -> cartLine.getProductId().equals(productId))
                .extracting(cartLine -> cartLine.getQuantity())
                .first()
                .isEqualTo(expectedQty);
    }

    @Test
    void itExposeCollectedItemsWithQuantity() {
        Cart cart = Cart.empty();
        String productId1 = thereIsProduct(PRODUCT_1);
        String productId2 = thereIsProduct(PRODUCT_2);

        cart.addProduct(productId1);
        cart.addProduct(productId1);
        cart.addProduct(productId1);

        cart.addProduct(productId2);

        List<CartLine> lines = cart.getLines();


        assertCartContainsQuantityForProduct(lines, PRODUCT_1, 3);
        assertCartContainsQuantityForProduct(lines, PRODUCT_2, 1);


    }
    private String thereIsProduct(String id) {
        return id;
    }
}
