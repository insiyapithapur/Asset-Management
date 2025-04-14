package com.elecon.asset_mgt.AssetRequest.Service;

import com.elecon.asset_mgt.AssetRequest.DAO.CreateAssetRequestDao;
import com.elecon.asset_mgt.AssetRequest.Models.AssetRequestModel;
import com.elecon.asset_mgt.AssetRequest.Models.StatusModel;
import com.elecon.asset_mgt.AssetRequest.Repository.AssetRequestRepository;
import com.elecon.asset_mgt.AssetRequest.Repository.StatusModelRepository;
import com.elecon.asset_mgt.Category.Models.CategoryModel;
import com.elecon.asset_mgt.Category.Repository.CategoryRepo;
import com.elecon.asset_mgt.Classification.Models.ClassificationModel;
import com.elecon.asset_mgt.Classification.Repository.ClassificationRepo;

import com.elecon.asset_mgt.Employee.Models.EmployeeModel;
import com.elecon.asset_mgt.Employee.Repository.EmployeeRepo;
import com.elecon.asset_mgt.Type.Models.TypeModel;
import com.elecon.asset_mgt.Type.Repository.TypeRepo;
import com.elecon.asset_mgt.location.Models.LocationModel;
import com.elecon.asset_mgt.location.Repository.LocationRepo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.*;

@Service
public class AssetRequestService {

    @Autowired private AssetRequestRepository repo;
    @Autowired private StatusModelRepository statusrepo;
    @Autowired private ClassificationRepo classrepo;
    @Autowired private LocationRepo locationrepo;
    @Autowired private CategoryRepo catrepo;
    @Autowired private TypeRepo typerepo;
    @Autowired private EmployeeRepo employeerepo;
    public String save(CreateAssetRequestDao assetreqdao,Principal principal){
        AssetRequestModel assetreqmodel = new AssetRequestModel();
        Date currentDate = new Date(System.currentTimeMillis());

        String employeeCodejwt = principal.getName();

        EmployeeModel employee = employeerepo.findByEmployeeCode(employeeCodejwt);

        if(Objects.equals(employee.getRole(), "employee"))
        {
            StatusModel status = statusrepo.findByStatus("Pending");
            assetreqmodel.setStatus(status);
        }
        if(Objects.equals(employee.getRole(), "manager"))
        {
            StatusModel status = statusrepo.findByStatus("Approved");
            assetreqmodel.setStatus(status);
        }

        ClassificationModel classification = classrepo.findById(assetreqdao.getClassification_id()).orElseThrow(() -> new IllegalArgumentException("ID classification doesn't exist"));
        LocationModel location =  locationrepo.findById(assetreqdao.getLocation_id()).orElseThrow(() -> new IllegalArgumentException("Invalid location ID"));
        CategoryModel category = catrepo.findById(assetreqdao.getCategory_id()).orElseThrow(() -> new IllegalArgumentException("Invalid category ID"));
        TypeModel type = typerepo.findById(assetreqdao.getAsset_type_id()).orElseThrow(() -> new IllegalArgumentException("Invalid type ID"));
        EmployeeModel reporting_to_id = employeerepo.findById(assetreqdao.getReporting_to_id()).orElseThrow(() -> new IllegalArgumentException("Invalid reporting_to ID"));

        assetreqmodel.setClassification_id(classification);
        assetreqmodel.setLocation_id(location);
        assetreqmodel.setCategory_id(category);
        assetreqmodel.setAsset_type_id(type);
        assetreqmodel.setEmployee(employee);
        assetreqmodel.setReporting_to_id(reporting_to_id);

        //set date from server side
        assetreqmodel.setUpdated_at(currentDate);
        BeanUtils.copyProperties(assetreqdao, assetreqmodel);

        repo.save(assetreqmodel);
        return "Done";
    }

    public List<AssetRequestModel> getAll() {
      return repo.findAll();
    }
    public Optional<AssetRequestModel> findById(Integer id) {
    return repo.findById(id);
  }
    public void deletebyID(Integer assetreqID,Principal principal){
        // check employeeId in database with employeeId in jwt
        repo.deleteById(assetreqID);
    }
  public void deleteSelected(List<Integer> ids) {
    List<AssetRequestModel> assetrequestsToDelete = repo.findAllById(ids);
    for (AssetRequestModel assetrequests : assetrequestsToDelete) {
      if (assetrequests != null) {
        repo.delete(assetrequests);
      }
    }

  } public void updateAssetRequests(AssetRequestModel updatedAssetRequest) {
    Integer assetRequID = updatedAssetRequest.getId();
    AssetRequestModel exisitingAssetRequest = repo.findById(assetRequID)
      .orElseThrow(() -> new AssetRequestNotFoundException("Asset Request not found with Id: " + assetRequID));
    System.out.println("updateAssetRequests Status ID: "+updatedAssetRequest.getClassification_id());

    exisitingAssetRequest.setAsset_type_id(updatedAssetRequest.getAsset_type_id());
    exisitingAssetRequest.setRequired_by(updatedAssetRequest.getRequired_by());
    exisitingAssetRequest.setDetails(updatedAssetRequest.getDetails());
    exisitingAssetRequest.setReason(updatedAssetRequest.getReason());
    exisitingAssetRequest.setStatus(updatedAssetRequest.getStatus());
    exisitingAssetRequest.setClassification_id(updatedAssetRequest.getClassification_id());
    exisitingAssetRequest.setCategory_id(updatedAssetRequest.getCategory_id());
    exisitingAssetRequest.setLocation_id(updatedAssetRequest.getLocation_id());
    exisitingAssetRequest.setEmployee(updatedAssetRequest.getEmployee());
    exisitingAssetRequest.setReporting_to_id(updatedAssetRequest.getReporting_to_id());
    exisitingAssetRequest.setAllocated_asset_id(updatedAssetRequest.getAllocated_asset_id());
    exisitingAssetRequest.setUpdated_at(new Date());
    repo.save(exisitingAssetRequest);
  }

    public  Optional<AssetRequestModel> updateAssetRequestToPullBack(Integer assetRequestsID, Principal principal) {
        String employeeCodejwt = principal.getName();
        EmployeeModel employee = employeerepo.findByEmployeeCode(employeeCodejwt);
        System.out.println("employee"+employee);
        Optional<AssetRequestModel> assestrequest = findById(assetRequestsID);
        System.out.println("asset request"+assestrequest);
        assestrequest.ifPresent(assetRequest -> {
            // Update the status from 'PENDING' to 'APPROVED'
            StatusModel approvedStatus = statusrepo.findByStatus("PullBack");
            System.out.println("approvedStatus"+approvedStatus);
            if (approvedStatus != null) {
                System.out.println("in if");
                assetRequest.getStatus().setId(approvedStatus.getId());
                assetRequest.getStatus().setStatus(approvedStatus.getStatus());
            } else {
                System.out.println("in else");
                return ;
            }

            // Set updated_at field to current timestamp
            assetRequest.setUpdated_at(new Date());

            // Save the updated AssetRequestModel back to the database
            repo.save(assetRequest);
        });

        return  assestrequest;
    }

    public  Optional<AssetRequestModel> updateAssetRequestToApproved(Integer assetRequestsID, Principal principal) {
        String employeeCodejwt = principal.getName();
        EmployeeModel employee = employeerepo.findByEmployeeCode(employeeCodejwt);
        System.out.println("employee"+employee);
        Optional<AssetRequestModel> assestrequest = findById(assetRequestsID);
        System.out.println("asset request"+assestrequest);

        assestrequest.ifPresent(assetRequest -> {
            // Update the status from 'PENDING' to 'APPROVED'
            StatusModel approvedStatus = statusrepo.findByStatus("Approved");
            if (approvedStatus != null) {
                assetRequest.getStatus().setId(approvedStatus.getId());
                assetRequest.getStatus().setStatus(approvedStatus.getStatus());
            } else {
                return ;
            }

            // Set updated_at field to current timestamp
            assetRequest.setUpdated_at(new Date());

            // Save the updated AssetRequestModel back to the database
            repo.save(assetRequest);
        });
        return  assestrequest;
    }
}
