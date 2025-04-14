package com.elecon.asset_mgt.Classification.Models;

import com.elecon.asset_mgt.Employee.Models.EmployeeModel;
import com.elecon.asset_mgt.location.Models.LocationModel;
import jakarta.persistence.*;
import lombok.*;

@Data
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "asset_classification")
public class ClassificationModel {
  @Id
  @GeneratedValue
  private Integer id;

  private boolean status; // true -> ACTIVE, false -> INACTIVE
  private String class_name;
  private String class_desc;

  @ManyToOne
  @JoinColumn(name = "owner_id", referencedColumnName = "id")
  private EmployeeModel owner;

  @ManyToOne
  @JoinColumn(name = "location_owner", referencedColumnName = "id")
  private EmployeeModel locationOwner;

  @ManyToOne
  @JoinColumn(name = "location", referencedColumnName = "id")
  private LocationModel location;
}
