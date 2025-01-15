package org.nomadly.backend.service;

import lombok.AllArgsConstructor;
import org.nomadly.backend.model.Category;
import org.nomadly.backend.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public void addCategory(Category category) {
        categoryRepository.save(category);
    }

    public void deleteCategoryById(Long id) {
        categoryRepository.deleteById(id);
    }
}
