package com.elecon.asset_mgt.Classification.Controllers;

import com.elecon.asset_mgt.Classification.Models.ClassificationModel;
import com.elecon.asset_mgt.Classification.Services.ClassificationNotFoundException;
import com.elecon.asset_mgt.Classification.Services.ClassificationService;
import com.elecon.asset_mgt.Exceptions.ForeignKeyViolationException;
import com.elecon.asset_mgt.location.Models.LocationModel;
import org.jetbrains.annotations.NotNull;
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
@RequestMapping("/api/v1/classification/")
public class ClassificationController {
  @Autowired
  private ClassificationService classificationService;

  @PostMapping("/CreateClassification")
  public ResponseEntity<Map<String, Object>> createClassification(@RequestBody ClassificationModel classificationModel) {
    try {
      classificationService.save(classificationModel);
      Map<String, Object> successResponse = new HashMap<>();
      successResponse.put("status", true);
      successResponse.put("message", "New classification is created successfully!");
      return ResponseEntity.status(HttpStatus.CREATED).body(successResponse);
    } catch (ForeignKeyViolationException e) {
      Map<String, Object> errorResponse = new HashMap<>();
      errorResponse.put("status", false);
      errorResponse.put("error", "selected attribute from dropdown is not valid!");
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    } catch (Exception e) {
      return handleException(e);
    }
  }


  @GetMapping("/")
  public ResponseEntity<Map<String, Object>> getClassifications() {
    try {
      List<ClassificationModel> result = classificationService.getAll();
      Map<String, Object> responseBody = new HashMap<>();
      responseBody.put("status", true);
      responseBody.put("data", result);
      return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    } catch (Exception e) {
      return handleException(e);
    }
  }

  @GetMapping("/{id}")
  public ResponseEntity<Map<String, Object>> getClassificationById(@PathVariable Integer id) {
    try {
      Optional<ClassificationModel> classification = classificationService.findById(id);
      if (classification.isPresent()) {
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("status", true);
        responseBody.put("data", classification.get());
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
      } else {
        throw new ClassificationNotFoundException("Classification not found with ID: " + id);
      }
    } catch (Exception e) {
      return handleException(e);
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Map<String, Object>> deleteClassificationById(@PathVariable Integer id) {
    try {
      classificationService.deleteById(id);
      Map<String, Object> successResponse = new HashMap<>();
      successResponse.put("status", true);
      successResponse.put("message", "Classification with ID " + id + " deleted successfully!");
      return ResponseEntity.status(HttpStatus.OK).body(successResponse);
    } catch (Exception e) {
      return handleException(e);
    }
  }

  @DeleteMapping("/deleteSelected")
  public ResponseEntity<Map<String, Object>> deleteSelectedClassifications(@RequestBody Map<String, List<Integer>> request) {
    try {
      List<Integer> ids = request.get("ids");
      classificationService.deleteSelected(ids);

      Map<String, Object> successResponse = new HashMap<>();
      successResponse.put("status", true);
      successResponse.put("message", "Selected classifications deleted successfully!");

      return ResponseEntity.status(HttpStatus.OK).body(successResponse);
    } catch (Exception e) {
      return handleException(e);
    }
  }

  @PutMapping("/updateClassification")
  public ResponseEntity<Map<String, Object>> updateClassification(@RequestBody ClassificationModel updatedClassification) {
    try {
      classificationService.updateClassification(updatedClassification);
      Map<String, Object> successResponse = new HashMap<>();
      successResponse.put("status", true);
      successResponse.put("message", "Classification updated successfully!");
      return ResponseEntity.status(HttpStatus.OK).body(successResponse);
    } catch (Exception e) {
      return handleException(e);
    }
  }

  @GetMapping("/findByLocation/{locationId}")
  public ResponseEntity<Map<String, Object>> getClassificationsByLocation(@PathVariable Integer locationId) {
    try {
      LocationModel location = new LocationModel();
      location.setId(locationId);

      List<ClassificationModel> classifications = classificationService.findByLocation(location);

      Map<String, Object> responseBody = new HashMap<>();
      responseBody.put("status", true);
      responseBody.put("data", classifications);

      return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    } catch (Exception e) {
      return handleException(e);
    }
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<Map<String, Object>> handleException(@NotNull Exception e) {
    Map<String, Object> errorResponse = new HashMap<>();
    errorResponse.put("status", false);
    errorResponse.put("error", e.getMessage());
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
  }

  @ExceptionHandler(ClassificationNotFoundException.class)
  public ResponseEntity<Map<String, Object>> handleClassificationNotFoundException(@NotNull ClassificationNotFoundException e) {
    Map<String, Object> errorResponse = new HashMap<>();
    errorResponse.put("status", false);
    errorResponse.put("error", e.getMessage());
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
  }
}
