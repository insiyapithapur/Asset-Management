package com.elecon.asset_mgt.Employee.Models;
import com.elecon.asset_mgt.Roles.Models.RoleModel;
import jakarta.persistence.*;
import lombok.*;
@Data
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "employees")

public class EmployeeModel  {
  @Id
  @GeneratedValue
  private Integer id;
  private String employee_name;
  private String employeeCode;
  private String employee_dept;
  private String employee_pass;
//  @ManyToOne
//  @JoinColumn(name = "role_id", referencedColumnName = "id")
  private String role;
  private boolean status;
  public EmployeeModel(Integer id) {
    this.id = id;
  }
  public EmployeeModel(String employeeCode, String employee_dept, String employee_name, boolean status, String role) {
    this.employeeCode = employeeCode;
    this.employee_dept = employee_dept;
    this.employee_name = employee_name;
    this.status = status;
//    this.role = new RoleModel(role.getId());
    this.role = role;
  }

}
