package com.elecon.asset_mgt.Type.Models;

import com.elecon.asset_mgt.Category.Models.CategoryModel;
import com.elecon.asset_mgt.Classification.Models.ClassificationModel;
import jakarta.persistence.*;
import lombok.*;

@Data
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "asset_type")
public class TypeModel {
    @Id
    @GeneratedValue
    private Integer id;
    private boolean status; // true -> ACTIVE, false -> INACTIVE
    private String type_name;
    private String type_desc;
    @ManyToOne
    @JoinColumn(name = "class_id", referencedColumnName = "id")
    private ClassificationModel class_id;
    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private CategoryModel category_id;
}
