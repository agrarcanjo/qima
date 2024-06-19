package br.com.qima.product.services;

import br.com.qima.product.dtos.CategoryDTO;
import br.com.qima.product.exceptions.AppException;
import br.com.qima.product.models.Category;
import br.com.qima.product.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Optional;

;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public List<Category> getAllCategorys() {
        return categoryRepository.findAll();
    }


    @Transactional(readOnly = true)
    public List<String> findAncestry(Long categoryId) {
        return categoryRepository.findAncestry(categoryId);
    }

    @Transactional(readOnly = true)
    public Category getCategoryById(Long categoryId) {
        Optional<Category> categoryOptional = categoryRepository.findById(categoryId);

        if (categoryOptional.isPresent()) {
            return categoryOptional.get();
        } else {
            throw new AppException("Category not found", HttpStatus.NOT_FOUND);
        }
    }

    @Transactional
    public Category add(CategoryDTO category) {
        if (category.getName() == null ||
                category.getName().isEmpty()) {
            throw new AppException("All fields are required.", HttpStatus.BAD_REQUEST);
        }
        return categoryRepository.save(category.toEntity());
    }

    public void updateCategory(CategoryDTO categoryDTO, Long categoryId) {
        Category category = getCategoryById(categoryId);

        if (!ObjectUtils.isEmpty(category)) {
            add(categoryDTO);
        } else {
            throw new AppException("Category not found", HttpStatus.NOT_FOUND);
        }
    }

    public void deleteCategory(Long categoryId) {
        Category category = getCategoryById(categoryId);

        if (!ObjectUtils.isEmpty(category)) {
            this.categoryRepository.delete(category);
        } else {
            throw new AppException("Category not found", HttpStatus.NOT_FOUND);
        }
    }
}
