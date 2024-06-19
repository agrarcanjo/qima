package br.com.qima.product.controllers;


import br.com.qima.product.models.Category;
import br.com.qima.product.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/v1/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("all")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<List<Category>> getAll() {
        return ResponseEntity.ok().body(categoryService.getAllCategorys());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> getProductById(@PathVariable Long categoryId) {
        return ResponseEntity.ok(categoryService.findAncestry(categoryId));
    }
}
