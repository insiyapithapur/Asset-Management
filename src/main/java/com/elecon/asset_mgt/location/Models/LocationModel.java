package com.elecon.asset_mgt.location.Models;

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
@Table(name = "locations")

public class LocationModel  {
  @Id
  @GeneratedValue
  private Integer id;
  private String location_name;
  private String company_name;
  private String department_name;
  private boolean status = false;
  public LocationModel(Integer id) {
    this.id = id;
  }
}
