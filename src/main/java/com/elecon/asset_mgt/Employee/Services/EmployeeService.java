package com.elecon.asset_mgt.Employee.Services;

import com.elecon.asset_mgt.Employee.Models.EmployeeModel;
import com.elecon.asset_mgt.Employee.Repository.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {
  @Autowired private EmployeeRepo repo;
//  public EmployeeModel findByEmployeeCode(String employee_code) {
//    return repo.findByemployee_code(employee_code);
//  }
  public EmployeeModel getEmployeeDetailsByEmployeeCode(String employee_code, String password) {
    EmployeeModel employeeDetails = repo.findByEmployeeCode(employee_code);
    if(employeeDetails != null && password.equals(employeeDetails.getEmployee_pass())) {
      return employeeDetails;
    }
    return null;
  }
  private String generatePassword() {
    // Implement your password generation logic here
    return "GeneratedPassword123"; // Dummy password for demonstration
  }
  public void registerEmployee(Integer employeeId){
    //Todo: Add Exception handling
    EmployeeModel employee = repo.findById(employeeId).orElse(null);
    if (employee != null && !employee.isStatus()) {
      String generatePassword = generatePassword();
      employee.setEmployee_pass(generatePassword);
      employee.setStatus(true);
      repo.save(employee);
    }
  }
  public void save(EmployeeModel employeemodel) {
    repo.save(employeemodel);
  }
  public List<EmployeeModel> getAll() {return repo.findAll();}

  public Optional<EmployeeModel> findById(Integer id){
    return repo.findById(id);
  }
  public void deleteById(Integer id){
    repo.deleteById(id);
  }
  public void deleteSelected(List<Integer> ids){
    List<EmployeeModel> employeesToDelete = repo.findAllById(ids);
    for (EmployeeModel employee : employeesToDelete){
      if( employee != null ) {
        repo.delete(employee);
      }
    }
  }
  public void updateEmployee(EmployeeModel updatedEmployee){
    Integer employeeId = updatedEmployee.getId();
    EmployeeModel existingEmployee = repo.findById(employeeId)
      .orElseThrow(() -> new EmployeeNotFoundException("Employee not found with Id: " + employeeId));
    existingEmployee.setEmployee_name(updatedEmployee.getEmployee_name());
    existingEmployee.setEmployee_dept(updatedEmployee.getEmployee_dept());
    existingEmployee.setEmployeeCode(updatedEmployee.getEmployeeCode());
    existingEmployee.setEmployee_pass(updatedEmployee.getEmployee_pass());
    existingEmployee.setStatus(updatedEmployee.isStatus());
    repo.save(existingEmployee);
  }
}
