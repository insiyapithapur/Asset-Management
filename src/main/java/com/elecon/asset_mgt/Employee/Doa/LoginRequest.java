package com.elecon.asset_mgt.Employee.Doa;

import lombok.Data;

@Data
public class LoginRequest {
  private String employeeCode;
  private String password;
}
