package br.com.qima.product.repository;

import br.com.qima.product.models.Product;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;


@DataJpaTest
@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ProductRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void testFindByAssociatedWithCategoryPath() {
        Product product1 = new Product();
        product1.setName("Product 1");
        entityManager.persist(product1);

        Product product2 = new Product();
        product2.setName("Product 2");
        entityManager.persist(product2);

        entityManager.flush();

        List<Product> foundProducts = productRepository.findByAssociatedWithCategoryPath();

        Assertions.assertThat(foundProducts.size()).isEqualTo(2);
        Assertions.assertThat(foundProducts).containsExactlyInAnyOrder(product1, product2);
    }
}


