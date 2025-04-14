package com.elecon.asset_mgt.AssetRequest.Models;

import com.elecon.asset_mgt.Category.Models.CategoryModel;
import com.elecon.asset_mgt.Classification.Models.ClassificationModel;
import com.elecon.asset_mgt.Employee.Models.EmployeeModel;
import com.elecon.asset_mgt.Type.Models.TypeModel;
import com.elecon.asset_mgt.location.Models.LocationModel;
import jakarta.persistence.*;
import lombok.*;
import java.util.Date;

@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Asset_Requests")
public class AssetRequestModel {
    @Id
    @GeneratedValue
    private Integer id;
    private String  allocated_asset_id;
    //at which date employee wants
    private Date required_by;
    //set by server side , update everytime when status update
    private Date updated_at;
    private String details;
    private String reason;
    @ManyToOne
    @JoinColumn(name = "status_id", referencedColumnName = "id")
    private StatusModel status;//[default: 'PENDING', note: "can be 'APPROVED', 'REJECTED', 'PENDING', 'ALLOCATED', 'PULLBACK', 'CANCELLED', 'HANDOVER'"]
    @ManyToOne
    @JoinColumn(name = "classification_id", referencedColumnName = "id")
    private ClassificationModel classification_id; //[note: 'for one classification, multiple requests can be done']
    @ManyToOne
    @JoinColumn(name = "location_id", referencedColumnName = "id")
    private LocationModel location_id;
    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private CategoryModel category_id;
    @ManyToOne
    @JoinColumn(name = "type_id", referencedColumnName = "id")
    private TypeModel asset_type_id;
    @ManyToOne
    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    private EmployeeModel employee;
    @ManyToOne
    @JoinColumn(name = "reporting_to_id", referencedColumnName = "id")
    private EmployeeModel reporting_to_id;


}
