package com.elecon.asset_mgt.AssetRequest.DAO;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Data
@Getter
@Setter
public class CreateAssetRequestDao {
    private String  allocated_asset_id;
    private String details;
    private String reason;
    private Integer classification_id;
    private Integer location_id;
    private Integer category_id;
    private Integer asset_type_id;
    private Integer reporting_to_id;
}
