package com.elecon.asset_mgt.location.Controllers;


import com.elecon.asset_mgt.location.Models.LocationModel;
import com.elecon.asset_mgt.location.Services.LocationNotFoundException;
import com.elecon.asset_mgt.location.Services.LocationService;
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
@RequestMapping("/api/v1/location")
public class LocationController {
  @Autowired
  private LocationService locationService;
  @CrossOrigin(origins = "*",allowedHeaders = "*")
  @PostMapping("/CreateLocation/")
  public ResponseEntity<Map<String, Object>> createEmployee(@RequestBody LocationModel locationModel) {
    try {
      locationService.save(locationModel);
      Map<String, Object> successResponse = new HashMap<>();
      successResponse.put("status", true);
      successResponse.put("message", "New location is created successfully!");
      return ResponseEntity.status(HttpStatus.CREATED).body(successResponse);
    } catch (Exception e) {
      return handleException(e);
    }
  }

  @CrossOrigin(origins = "*",allowedHeaders = "*")
  @GetMapping("/")
  public ResponseEntity<Map<String, Object>> getLocation() {
    try {
      List<LocationModel> result = locationService.getAll();
      Map<String, Object> responseBody = new HashMap<>();
      responseBody.put("status", true);
      responseBody.put("data", result);
      return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    } catch (Exception e) {
      return handleException(e);
    }
  }

  @GetMapping("/{id}")
  public ResponseEntity<Map<String, Object>> getLocationById(@PathVariable Integer id) {
    try {
      Optional<LocationModel> location = locationService.findById(id);
      if (location.isPresent()) {
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("status", true);
        responseBody.put("data", location.get());
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
      } else {
        throw new LocationNotFoundException("Location not found with ID: " + id);
      }
    } catch (Exception e) {
      return handleException(e);
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Map<String, Object>> deleteLocationById(@PathVariable Integer id) {
    try {
      locationService.deleteById(id);
      Map<String, Object> successResponse = new HashMap<>();
      successResponse.put("status", true);
      successResponse.put("message", "Location with ID " + id + " deleted successfully!");
      return ResponseEntity.status(HttpStatus.OK).body(successResponse);
    } catch (Exception e) {
      return handleException(e);
    }
  }

  @DeleteMapping("/deleteSelected")
  public ResponseEntity<Map<String, Object>> deleteSelectedLocations(@RequestBody Map<String, List<Integer>> request) {
    try {
      List<Integer> ids = request.get("ids");
      locationService.deleteSelected(ids);

      Map<String, Object> successResponse = new HashMap<>();
      successResponse.put("status", true);
      successResponse.put("message", "Selected locations deleted successfully!");

      return ResponseEntity.status(HttpStatus.OK).body(successResponse);
    } catch (Exception e) {
      return handleException(e);
    }
  }

  @PutMapping("/updateLocation")
  public ResponseEntity<Map<String, Object>> updateCategory(@RequestBody LocationModel updateLocation) {
    try {
      locationService.updateLocation(updateLocation);
      Map<String, Object> successResponse = new HashMap<>();
      successResponse.put("status", true);
      successResponse.put("message", "Location updated successfully!");
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

  @ExceptionHandler(LocationNotFoundException.class)
  public ResponseEntity<Map<String, Object>> handleLocationNotFoundException(LocationNotFoundException e) {
    Map<String, Object> errorResponse = new HashMap<>();
    errorResponse.put("status", false);
    errorResponse.put("error", e.getMessage());
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
  }
}

