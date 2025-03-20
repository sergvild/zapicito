package com.ruso.zapicito.service;

import com.ruso.zapicito.dto.CategoryDto;
import com.ruso.zapicito.entity.*;
import com.ruso.zapicito.exception.ZapicitoException;
import com.ruso.zapicito.mapper.CategoryMapper;
import com.ruso.zapicito.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }

    public List<Category> findAllCategories(){
        return categoryRepository.findAll();
    }

    public List<Category> findCategoriesByIds(List<Long> categoryIds) {
        return categoryRepository.findAllById(categoryIds);
    }

    public Category updateCategory(Category updatedCategory, Long categoryId) throws ZapicitoException {
        categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ZapicitoException("Couldn't find category by ID="+categoryId));

        updatedCategory.setId(categoryId);
        updatedCategory.setUpdatedDate(Timestamp.valueOf(LocalDateTime.now()));
        return categoryRepository.save(updatedCategory);
    }

    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }

    public Category mapToCategory(CategoryDto categoryDto) {
        return categoryMapper.fromDto(categoryDto);
    }
}
