package com.elecon.asset_mgt.Roles.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Data
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "roles")

public class RoleModel {
  @Id
  @GeneratedValue
  private Integer id;
  private String role_name;
  public RoleModel (Integer id){
    this.id = id;
  }
}
