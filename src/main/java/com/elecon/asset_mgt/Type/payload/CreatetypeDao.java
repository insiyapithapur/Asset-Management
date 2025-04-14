package com.elecon.asset_mgt.Type.payload;

import com.elecon.asset_mgt.Category.Models.CategoryModel;
import lombok.Data;

@Data
public class CreatetypeDao {
    private boolean status; // true -> ACTIVE, false -> INACTIVE
    private String type_name;
    private String type_desc;
    private Integer class_id;
    private Integer category_id;
}
