package br.com.qima.product.services;

import br.com.qima.product.dtos.ProductDTO;
import br.com.qima.product.exceptions.AppException;
import br.com.qima.product.models.Category;
import br.com.qima.product.models.Product;
import br.com.qima.product.repository.CategoryRepository;
import br.com.qima.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public List<Product> getAllProducts() {
        List<Product> all = productRepository.findAll();
        return all;
    }

    @Transactional(readOnly = true)
    public Product getProductById(Long productId) {
        Optional<Product> productOptional = productRepository.findById(productId);

        if (productOptional.isPresent()) {
            return productOptional.get();
        } else {
            throw new AppException("Product not found", HttpStatus.NOT_FOUND);
        }
    }

    @Transactional
    public Product add(ProductDTO product) {
        if (product.getName() == null ||
                product.getName().isEmpty() ||
                product.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new AppException("All fields are required.", HttpStatus.BAD_REQUEST);
        }

        Product newProduct = product.toEntity();

         if (!ObjectUtils.isEmpty(product.getCategory())) {
            Optional<Category> byId = categoryRepository.findById(product.getCategory());
            newProduct.setCategories(Collections.singletonList(byId.get()));
        }

        return productRepository.save(newProduct);
    }

    @Transactional
    public void updateProduct(ProductDTO productDTO, Long productId) {
        Product product = getProductById(productId);

        if (!ObjectUtils.isEmpty(product)) {
            add(productDTO);
        } else {
            throw new AppException("Product not found", HttpStatus.NOT_FOUND);
        }
    }
    @Transactional
    public void deleteProduct(Long productId) {
        Product product = getProductById(productId);

        if (!ObjectUtils.isEmpty(product)) {
            product.setCategories(null);
            this.productRepository.delete(product);
        } else {
            throw new AppException("Product not found", HttpStatus.NOT_FOUND);
        }
    }
}
