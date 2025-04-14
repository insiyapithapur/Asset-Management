package com.elecon.asset_mgt.Category.Models;
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
@Table(name = "asset_category")

public class CategoryModel  {
  @Id
  @GeneratedValue
  private Integer id;
  private boolean status = false; //true -> ACTIVE, false -> INACTIVE
  private String category_name;
  private String category_desc;
}
