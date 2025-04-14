package com.elecon.asset_mgt.location.Services;

import com.elecon.asset_mgt.Employee.Services.EmployeeNotFoundException;

public class LocationNotFoundException extends RuntimeException{
  public LocationNotFoundException(String message) {
    super(message);
  }
}
