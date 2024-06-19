package br.com.qima.product.services;

import br.com.qima.product.exceptions.AppException;
import br.com.qima.product.models.Product;
import br.com.qima.product.repository.CategoryRepository;
import br.com.qima.product.repository.ProductRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @MockBean
    private ProductRepository productRepository;

    @MockBean
    private CategoryRepository categoryRepository;

    @Test
    public void testGetAllProducts() {
        Product product = new Product();
        product.setName("Product");
        List<Product> productList = Arrays.asList(product);

        Mockito.when(productRepository.findAll()).thenReturn(productList);

        List<Product> result = productService.getAllProducts();
        Assert.assertEquals(result.size(), 1);
        Assert.assertEquals(result.get(0).getName(), "Product");
    }

    @Test
    public void testGetProductById() {
        Product product = new Product();
        product.setName("Product");

        Mockito.when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        Product result = productService.getProductById(1L);
        Assert.assertEquals(result.getName(), "Product");
    }

    @Test(expected = AppException.class)
    public void testGetProductByIdNotFound() {
        Mockito.when(productRepository.findById(1L)).thenReturn(Optional.empty());

        productService.getProductById(1L);
    }
}
