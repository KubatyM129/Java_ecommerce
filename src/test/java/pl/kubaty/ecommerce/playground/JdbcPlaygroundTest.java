package pl.kubaty.ecommerce.playground;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import pl.kubaty.ecommerce.catalog.Product;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class JdbcPlaygroundTest {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void itSetupDb() {
        var dropTableIfExist = "drop table `product_catalog__products` if exists";
        jdbcTemplate.execute(dropTableIfExist);

        var createTableSql = "create table `product_catalog__products` (" +
                "`id` varchar(255) NOT NULL," +
                "`name` varchar(255) NOT NULL," +
                "`price` DECIMAL(12,2)," +
                "PRIMARY KEY (id)" +
                ")";

        jdbcTemplate.execute(createTableSql);
    }

    @Test
    void selectMyNameViaDB() {
        var currentDate = jdbcTemplate.queryForObject(
                "select now() my_date",
                String.class
        );

        assert currentDate.contains("2024");
    }


    @Test
    void itCountsProductsWhenNoProducts() {
        var countSql = "select count(*) from `product_catalog__products`";
        int productCount = jdbcTemplate.queryForObject(countSql, Integer.class);

        assertThat(productCount).isEqualTo(0);
    }

    @Test
    void itAllowsToInsertElements() {
        var insertSql = "insert into `product_catalog__products` (id, name, price)" +
                "values ('product_1', 'my 1 product', 20.25)" +
                ";";
        jdbcTemplate.execute(insertSql);

        var countSql = "select count(*) from `product_catalog__products`";
        int productCount = jdbcTemplate.queryForObject(countSql, Integer.class);

        assertThat(productCount).isEqualTo(1);

    }

    @Test
    void itAllowsToInsertDynamicElements() {
        var insertSql = "insert into `product_catalog__products` (id, name, price)" +
                "values (?,?,?)" +
                ";";
        var product = new Product(UUID.randomUUID(), "my product", "xyz", BigDecimal.valueOf(10));


        jdbcTemplate.update(insertSql, product.getId(), product.getName(), product.getPrice());


        var countSql = "select count(*) from `product_catalog__products`";
        int productCount = jdbcTemplate.queryForObject(countSql, Integer.class);

        assertThat(productCount).isEqualTo(1);

    }

    @Test
    void selectForProduct() {
        var insertSql = "insert into `product_catalog__products` (id, name, price)" +
                "values ('product_1', 'my 1 product', 20.25)" +
                ";";
        jdbcTemplate.execute(insertSql);

        var selectSql = "select * from `product_catalog__products` where id = ?";

        Product myProduct = jdbcTemplate.queryForObject(
                selectSql,
                new Object[] {"product_1"},
                (rs,i) -> {
                    var myResult = new Product(
                            UUID.randomUUID(),
                            rs.getString("name"),
                            rs.getString("name"),
                            rs.getBigDecimal("price")
                    );


                        return myResult;
                }
        );

        assertThat(myProduct.getName()).isEqualTo("my 1 product");
    }


}
