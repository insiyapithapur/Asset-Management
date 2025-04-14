package com.elecon.asset_mgt.Type.Controllers;

import com.elecon.asset_mgt.Classification.Services.ClassificationNotFoundException;
import com.elecon.asset_mgt.Exceptions.ForeignKeyViolationException;
import com.elecon.asset_mgt.Type.Models.TypeModel;
import com.elecon.asset_mgt.Type.Services.TypeService;
import com.elecon.asset_mgt.Type.payload.CreatetypeDao;
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
@RequestMapping("/api/v1/type/")
public class TypeController {
    @Autowired
    private TypeService typeservice;
    @PostMapping("/Createtype")
    public ResponseEntity<Map<String, Object>> createtype(@RequestBody CreatetypeDao typemodeldao) {
        try {
            System.out.println("typemodel "+typemodeldao);
            typeservice.save(typemodeldao);
            Map<String, Object> successResponse = new HashMap<>();
            successResponse.put("status", true);
            successResponse.put("message", "New type is created successfully!");
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
    public ResponseEntity<Map<String, Object>> getType() {
        try {
            List<TypeModel> result = typeservice.getAll();
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("status", true);
            responseBody.put("data", result);
            return ResponseEntity.status(HttpStatus.OK).body(responseBody);
        } catch (Exception e) {
            return handleException(e);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getTypeById(@PathVariable Integer id) {
        try {
            Optional<TypeModel> type = typeservice.findById(id);
            if (type.isPresent()) {
                Map<String, Object> responseBody = new HashMap<>();
                responseBody.put("status", true);
                responseBody.put("data", type.get());
                return ResponseEntity.status(HttpStatus.OK).body(responseBody);
            } else {
                throw new ClassificationNotFoundException("type not found with ID: " + id);
            }
        } catch (Exception e) {
            return handleException(e);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deletetypeById(@PathVariable Integer id) {
        try {
            typeservice.deleteById(id);
            Map<String, Object> successResponse = new HashMap<>();
            successResponse.put("status", true);
            successResponse.put("message", "type with ID " + id + " deleted successfully!");
            return ResponseEntity.status(HttpStatus.OK).body(successResponse);
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
}
