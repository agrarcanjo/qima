package br.com.qima.product.services;

import br.com.qima.product.dtos.ProductDTO;
import br.com.qima.product.dtos.ProjectQuery;
import br.com.qima.product.exceptions.AppException;
import br.com.qima.product.models.Category;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
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

    @Test
    public void testGetAllProductsPageable() {

        Product product = new Product();
        product.setName("Product");
        List<Product> productList = Arrays.asList(product);

        Mockito.when(productService.getAllProductsPageable(Mockito.any(), Mockito.any()))
                .thenReturn(Page.empty());

        ProjectQuery query = new ProjectQuery();
        Page<Product> products = productService.getAllProductsPageable(query, PageRequest.of(0, 5));
        Assert.assertTrue(products.isEmpty());
    }

    @Test
    public void testAdd() {
        ProductDTO productDTO = new ProductDTO();

        productDTO.setName("Product Name");
        productDTO.setPrice(BigDecimal.TEN);

        Mockito.when(categoryRepository.findById(Mockito.any())).thenReturn(Optional.of(new Category()));
        Mockito.when(productRepository.save(Mockito.any())).thenReturn(new Product());

        Product product = productService.add(productDTO);

        Mockito.verify(productRepository, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    public void testUpdateProduct() {

        ProductDTO productDTO = new ProductDTO();
        productDTO.setName("Product Name");
        productDTO.setPrice(BigDecimal.TEN);

        Mockito.when(productRepository.findById(Mockito.any())).thenReturn(Optional.of(new Product()));
        Mockito.when(productRepository.save(Mockito.any())).thenReturn(new Product());

        productService.updateProduct(productDTO, 1L);

        Mockito.verify(productRepository, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    public void testDeleteProduct() {
        Product product = new Product();

        Mockito.when(productRepository.findById(Mockito.any())).thenReturn(Optional.of(product));

        productService.deleteProduct(1L);

        Mockito.verify(productRepository, Mockito.times(1)).delete(Mockito.any());
    }
}
