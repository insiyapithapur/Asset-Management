package com.elecon.asset_mgt.Category.Controllers;

import com.elecon.asset_mgt.Category.Models.CategoryModel;
import com.elecon.asset_mgt.Category.Service.CategoryNotFoundException;
import com.elecon.asset_mgt.Category.Service.CategoryService;
import com.elecon.asset_mgt.Category.dao.updateStatusDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/v1/category")
public class CategoryController {
  @Autowired
  private CategoryService categoryService;
  @CrossOrigin(origins = "*",allowedHeaders = "*")
  @PostMapping("/CreateCategory/")
  public ResponseEntity<Map<String, Object>> createCategory(@RequestBody CategoryModel categoryModel) {
    System.out.println("in createEmployee");
    try {
      System.out.println("in try block");
      categoryService.save(categoryModel);
      Map<String, Object> successResponse = new HashMap<>();
      successResponse.put("status", true);
      successResponse.put("message", "New category is created successfully!");
      return ResponseEntity.status(HttpStatus.CREATED).body(successResponse);
    } catch (Exception e) {
      System.out.println("in catch block");
      return handleException(e);
    }
  }

  @PostMapping ("/updateStatus")
  public ResponseEntity<Map<String, Object>> updateStatus(@RequestBody updateStatusDao updatestatusdao) {
    try {
      categoryService.updateStatus(updatestatusdao);
      Map<String, Object> successResponse = new HashMap<>();
      successResponse.put("status", true);
      successResponse.put("message", "Category updated successfully!");
      return ResponseEntity.status(HttpStatus.OK).body(successResponse);
    } catch (Exception e) {
      return handleException(e);
    }
  }

  @CrossOrigin(origins = "*",allowedHeaders = "*")
  @GetMapping("/")
  public ResponseEntity<Map<String, Object>> getCategory() {
    try {
      List<CategoryModel> result = categoryService.getAll();
      Map<String, Object> responseBody = new HashMap<>();
      responseBody.put("status", true);
      responseBody.put("data", result);
      return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    } catch (Exception e) {
      return handleException(e);
    }
  }

  @GetMapping("/{id}")
  public ResponseEntity<Map<String, Object>> getCategoryById(@PathVariable Integer id) {
    try {
      Optional<CategoryModel> category = categoryService.findById(id);
      if (category.isPresent()) {
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("status", true);
        responseBody.put("data", category.get());
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
      } else {
        throw new CategoryNotFoundException("Category not found with ID: " + id);
      }
    } catch (Exception e) {
      return handleException(e);
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Map<String, Object>> deleteCategoryById(@PathVariable Integer id) {
    try {
      categoryService.deleteById(id);
      Map<String, Object> successResponse = new HashMap<>();
      successResponse.put("status", true);
      successResponse.put("message", "Category with ID " + id + " deleted successfully!");
      return ResponseEntity.status(HttpStatus.OK).body(successResponse);
    } catch (Exception e) {
      return handleException(e);
    }
  }

  @DeleteMapping("/deleteSelected")
  public ResponseEntity<Map<String, Object>> deleteSelectedCategories(@RequestBody Map<String, List<Integer>> request) {
    try {
      List<Integer> ids = request.get("ids");
      System.out.println("Type of ids: " + ids.getClass().getName());
      categoryService.deleteSelected(ids);

      Map<String, Object> successResponse = new HashMap<>();
      successResponse.put("status", true);
      successResponse.put("message", "Selected categories deleted successfully!");

      return ResponseEntity.status(HttpStatus.OK).body(successResponse);
    } catch (Exception e) {
      return handleException(e);
    }
  }

  @PutMapping("/updateCategory")
  public ResponseEntity<Map<String, Object>> updateCategory(@RequestBody CategoryModel updatedCategory) {
    try {
      categoryService.updateCategory(updatedCategory);
      Map<String, Object> successResponse = new HashMap<>();
      successResponse.put("status", true);
      successResponse.put("message", "Category updated successfully!");
      return ResponseEntity.status(HttpStatus.OK).body(successResponse);
    } catch (Exception e) {
      return handleException(e);
    }
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<Map<String, Object>> handleException(Exception e) {
    Map<String, Object> errorResponse = new HashMap<>();
    errorResponse.put("status", false);
    errorResponse.put("error", e.getMessage());
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
  }

  @ExceptionHandler(CategoryNotFoundException.class)
  public ResponseEntity<Map<String, Object>> handleCategoryNotFoundException(CategoryNotFoundException e) {
    Map<String, Object> errorResponse = new HashMap<>();
    errorResponse.put("status", false);
    errorResponse.put("error", e.getMessage());
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
  }
}
