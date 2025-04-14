package com.elecon.asset_mgt.Employee.DAO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDetails {
  private String employeeName;
  private String employeeCode;
  private String role;
  private String password; // This should be encrypted
}
