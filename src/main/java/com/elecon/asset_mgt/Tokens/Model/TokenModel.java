package com.elecon.asset_mgt.Tokens.Model;

import com.elecon.asset_mgt.Employee.Models.EmployeeModel;
import jakarta.persistence.*;
import lombok.*;

@Data
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Tokens")

public class TokenModel {
  @Id
  @GeneratedValue
  private Integer id;
  @Column(unique = true)
  private String token;
  private boolean status;
  @ManyToOne
  @JoinColumn(name = "employee_id", referencedColumnName = "id")
  private EmployeeModel employee_id;
}
