package com.elecon.asset_mgt.utils;


import com.elecon.asset_mgt.Employee.DAO.LoginResponse;
import com.elecon.asset_mgt.Employee.Models.EmployeeModel;
import com.elecon.asset_mgt.Employee.Services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
  @Autowired
  private EmployeeService employeeService;
  @Autowired
  private jwtService jwtservice;

  private final String SECRET_KEY = "afafasfafafasfasfasfafacasdasfasxASFACASDFACASDFASFASFDAFASFASDAADSCSDFADCVSGCFVADXCcadwavfsfarvf"; // Change this to your secret key

  public LoginResponse authenticate(String employeeCode, String password) {
    EmployeeModel employeeDetails = employeeService.getEmployeeDetailsByEmployeeCode(employeeCode, password);

    if (employeeDetails != null && password.equals(employeeDetails.getEmployee_pass())) {
      String token = jwtservice.generateToken(employeeDetails);

      return new LoginResponse(token, employeeDetails);
    } else {
      return null;
    }
  }

}
