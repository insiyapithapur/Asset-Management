// The EmployeeController class contains methods for creating, retrieving, updating, and deleting employee data, handling exceptions, and authenticating login requests in an employee management system.
package com.elecon.asset_mgt.Employee.Controllers;

import com.elecon.asset_mgt.Employee.DAO.LoginRequest;
import com.elecon.asset_mgt.Employee.DAO.LoginResponse;
import com.elecon.asset_mgt.Employee.Models.EmployeeModel;
import com.elecon.asset_mgt.Employee.Repository.EmployeeRepo;
import com.elecon.asset_mgt.Employee.Services.EmployeeNotFoundException;
import com.elecon.asset_mgt.Employee.Services.EmployeeService;
import com.elecon.asset_mgt.Exceptions.ForeignKeyViolationException;
import com.elecon.asset_mgt.utils.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/v1/employee")
public class EmployeeController {
  @Autowired
  private EmployeeService employeeService;
  @Autowired
  private AuthenticationService authenticationService;
  @Autowired
  private EmployeeRepo employeerepo;
  @CrossOrigin(origins = "*",allowedHeaders = "*")
  @PostMapping("/CreateEmployee/")
  public ResponseEntity<Map<String, Object>> createEmployee(@RequestBody EmployeeModel employeeModel) {
    try {
      //Todo: Add Exception handling
      //Create Exception
//      EmployeeModel existingEmployee = employeeService.findByEmployeeCode(employeeModel.getEmployee_code());
//      if (existingEmployee != null && !existingEmployee.isStatus()) {
//        return  Exception
//      }
      employeeService.save(employeeModel);
      Map<String, Object> successResponse = new HashMap<>();
      successResponse.put("status", true);
      successResponse.put("message", "New employee is created successfully!");
      return ResponseEntity.status(HttpStatus.CREATED).body(successResponse);
    }catch(ForeignKeyViolationException e){
      Map<String, Object> errorResponse = new HashMap<>();
      errorResponse.put("status", false);
      errorResponse.put("error", "selected attribute from dropdown is not valid!");
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
    catch (Exception e) {
      return handleException(e);
    }
  }


  @CrossOrigin(origins = "*",allowedHeaders = "*")
  @GetMapping("/")
  public ResponseEntity<Map<String, Object>> getEmployee() {
    try {
      List<EmployeeModel> result = employeeService.getAll();
      Map<String, Object> responseBody = new HashMap<>();
      responseBody.put("status", true);
      responseBody.put("data", result);
      return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    } catch (Exception e) {
      return handleException(e);
    }
  }
  @PostMapping("/login")
  public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
    String employeeCode = loginRequest.getEmployeeCode();
    String password = loginRequest.getPassword();

    LoginResponse loginResponse = authenticationService.authenticate(employeeCode, password);

    if (loginResponse != null) {
      return ResponseEntity.ok(loginResponse);
    } else {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
        .body(new LoginResponse("Invalid credentials",null));
    }
  }
  @GetMapping("/registerEmployee/{employeeId}")
  public ResponseEntity<String> registerEmployee(@PathVariable Integer employeeId){
    try{
      Optional<EmployeeModel> employee = employeeService.findById(employeeId);
      employeeService.registerEmployee(employeeId);
      return ResponseEntity.status(HttpStatus.OK).body("Employee successfully registered");
    }
    catch (EmployeeNotFoundException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    catch(Exception e){
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred.");
    }
  }

  @GetMapping("/{id}")
  public ResponseEntity<Map<String, Object>> getEmployeeById(@PathVariable Integer id) {
    try {
      Optional<EmployeeModel> employee = employeeService.findById(id);
      if (employee.isPresent()) {
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("status", true);
        responseBody.put("data", employee.get());
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
      } else {
        throw new EmployeeNotFoundException("Employee not found with ID: " + id);
      }
    } catch (Exception e) {
      return handleException(e);
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Map<String, Object>> deleteEmployeeById(@PathVariable Integer id) {
    try {
      employeeService.deleteById(id);
      Map<String, Object> successResponse = new HashMap<>();
      successResponse.put("status", true);
      successResponse.put("message", "Employee with ID " + id + " deleted successfully!");
      return ResponseEntity.status(HttpStatus.OK).body(successResponse);
    } catch (Exception e) {
      return handleException(e);
    }
  }

  @DeleteMapping("/deleteSelected")
  public ResponseEntity<Map<String, Object>> deleteSelectedEmployees(@RequestBody Map<String, List<Integer>> request) {
    try {
      List<Integer> ids = request.get("ids");
      employeeService.deleteSelected(ids);

      Map<String, Object> successResponse = new HashMap<>();
      successResponse.put("status", true);
      successResponse.put("message", "Selected employees deleted successfully!");

      return ResponseEntity.status(HttpStatus.OK).body(successResponse);
    } catch (Exception e) {
      return handleException(e);
    }
  }

  @PutMapping("/updateEmployee")
  public ResponseEntity<Map<String, Object>> updateCategory(@RequestBody EmployeeModel updatedEmployee) {
    try {
      employeeService.updateEmployee(updatedEmployee);
      Map<String, Object> successResponse = new HashMap<>();
      successResponse.put("status", true);
      successResponse.put("message", "Employee updated successfully!");
      return ResponseEntity.status(HttpStatus.OK).body(successResponse);
    } catch (Exception e) {
      return handleException(e);
    }
  }

  @GetMapping("/test/")
  public ResponseEntity<?> test(Principal principal) {
    String employeeCodejwt = principal.getName();
    System.out.println(employeeCodejwt);
    EmployeeModel employee = employeerepo.findByEmployeeCode(employeeCodejwt);
    return ResponseEntity.status(HttpStatus.OK).body(employee);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<Map<String, Object>> handleException(Exception e) {
    Map<String, Object> errorResponse = new HashMap<>();
    errorResponse.put("status", false);
    errorResponse.put("error", e.getMessage());
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
  }

  @ExceptionHandler(EmployeeNotFoundException.class)
  public ResponseEntity<Map<String, Object>> handleEmployeeNotFoundException(EmployeeNotFoundException e) {
    Map<String, Object> errorResponse = new HashMap<>();
    errorResponse.put("status", false);
    errorResponse.put("error", e.getMessage());
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
  }
}

