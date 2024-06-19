package br.com.qima.product.controllers;


import br.com.qima.product.dtos.ProductDTO;
import br.com.qima.product.models.Product;
import br.com.qima.product.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

;

@RestController
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RequestMapping("/api/v1/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("all")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<List<Product>> getAll() {
        return ResponseEntity.ok().body(productService.getAllProducts());
    }

    @PostMapping("add")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public Product add(@RequestBody ProductDTO product) {
        return productService.add(product);
    }

    @GetMapping("/{productId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> getProductById(@PathVariable Long productId) {
        return ResponseEntity.ok(productService.getProductById(productId));
    }

    @DeleteMapping(path = "/{productId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Void> deletarContrato(@PathVariable Long productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(path = "/{productId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Void> atualizarContrapartida
            (@RequestBody ProductDTO product, @PathVariable Long productId) {
        productService.updateProduct(product, productId);
        return ResponseEntity.ok().build();
    }

}
