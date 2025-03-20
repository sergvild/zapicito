package com.ruso.zapicito.controller;

import com.ruso.zapicito.dto.ApiResponse;
import com.ruso.zapicito.dto.CategoryDto;
import com.ruso.zapicito.entity.Category;
import com.ruso.zapicito.exception.ZapicitoException;
import com.ruso.zapicito.service.CategoryService;
import com.ruso.zapicito.util.ResponseUtil;
import io.swagger.annotations.ApiParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Category>> createCategory(@RequestBody CategoryDto categoryDto) throws ZapicitoException {
        Category category = categoryService.mapToCategory(categoryDto);

        return new ResponseEntity<>(ResponseUtil.success(categoryService.createCategory(category)),
                HttpStatus.ACCEPTED);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Category>>> findAllCategories(){
        List<Category> categories = categoryService.findAllCategories();
        return new ResponseEntity<>(ResponseUtil.success(categories), HttpStatus.ACCEPTED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Category>> updateCategory(@RequestBody CategoryDto categoryDto,
                                                   @PathVariable @ApiParam(name = "id", value = "Category id", example = "1") Long id) throws ZapicitoException {

        Category updatedCategory = categoryService.mapToCategory(categoryDto);
        Category category = categoryService.updateCategory(updatedCategory, id);

        return new ResponseEntity<>(ResponseUtil.success(category), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteCategory(@PathVariable @ApiParam(name = "id", value = "Category id", example = "1") Long id){
        categoryService.deleteCategory(id);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

}
