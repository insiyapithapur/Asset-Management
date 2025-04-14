package com.elecon.asset_mgt.Employee.DAO;

import com.elecon.asset_mgt.Employee.Models.EmployeeModel;
import lombok.Data;

@Data

public class LoginResponse{
  private String token;
  private EmployeeModel employeeDetails;

  public LoginResponse(String token, EmployeeModel employeeDetails) {
    this.token = token;
    this.employeeDetails = employeeDetails;
  }
}
