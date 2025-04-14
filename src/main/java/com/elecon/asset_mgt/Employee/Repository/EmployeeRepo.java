package com.elecon.asset_mgt.Employee.Repository;

import com.elecon.asset_mgt.Employee.Models.EmployeeModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepo  extends JpaRepository<EmployeeModel,Integer> {
    EmployeeModel findByEmployeeCode(String employeeCode);

}
