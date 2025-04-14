package com.elecon.asset_mgt.AssetRequest.Controllers;

import com.elecon.asset_mgt.AssetRequest.DAO.CreateAssetRequestDao;
import com.elecon.asset_mgt.AssetRequest.Models.AssetRequestModel;
import com.elecon.asset_mgt.AssetRequest.Models.StatusModel;
import com.elecon.asset_mgt.AssetRequest.Repository.StatusModelRepository;
import com.elecon.asset_mgt.AssetRequest.Service.AssetRequestService;
import com.elecon.asset_mgt.Classification.Models.ClassificationModel;
import com.elecon.asset_mgt.Classification.Services.ClassificationNotFoundException;
import com.elecon.asset_mgt.Exceptions.ForeignKeyViolationException;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/v1/AssetRequest/")
public class AssetController {
    @Autowired
    private AssetRequestService assetreqservice;

    @Autowired
    private StatusModelRepository statusrepo;

    @PostMapping("createAssetRequest/")
    public ResponseEntity<?> createAssetRequest(@RequestBody CreateAssetRequestDao assetreqdao , Principal principal) {
        System.out.println("in create");
        try {
            System.out.println("in create asset");
            String response = assetreqservice.save(assetreqdao,principal);
            Map<String, Object> successResponse = new HashMap<>();
            successResponse.put("status", true);
            successResponse.put("message", "New asset request is created successfully!");
            successResponse.put("serviceResponse", response);
            return ResponseEntity.status(HttpStatus.CREATED).body(successResponse);
        } catch (Exception e) {
            return handleException(e);
        }
    }
  @GetMapping("/")
  public ResponseEntity<Map<String, Object>> getAllAssetsRequests() {
    try {
      List<AssetRequestModel> result = assetreqservice.getAll();
      Map<String, Object> responseBody = new HashMap<>();
      responseBody.put("status", true);
      responseBody.put("data", result);
      return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    } catch (Exception e) {
      return handleException(e);
    }
  }

  @GetMapping("/{id}")
  public ResponseEntity<Map<String, Object>> getAssetsRequestsById(@PathVariable Integer id) {
    try {
      Optional<AssetRequestModel> all_asset_requests = assetreqservice.findById(id);
      if (all_asset_requests.isPresent()) {
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("status", true);
        responseBody.put("data", all_asset_requests.get());
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
      } else {
        throw new ClassificationNotFoundException("Classification not found with ID: " + id);
      }
    } catch (Exception e) {
      return handleException(e);
    }
  }


//    stage:
//            1) pullback, cancel -- comment
//            2) manager approve, reject -- comment
//            3) allocation approve, reject -- comment
//            4) handover -- comment

    //can be can celled api for employee or manager whoever is requesting
    @DeleteMapping("/{assetreqID}")
    public ResponseEntity<?> deleteAssetRequest(@PathVariable Integer assetreqID,Principal principal){
        System.out.println("in delete");
        try {
            assetreqservice.deletebyID(assetreqID,principal) ;
            Map<String, Object> successResponse = new HashMap<>();
            successResponse.put("status", true);
            successResponse.put("message", "Delete asset request  successfully!");
            return ResponseEntity.status(HttpStatus.OK).body(successResponse);
        } catch (Exception e) {
             return handleException(e);
        }
    }
  @DeleteMapping("/deleteSelected")
  public ResponseEntity<Map<String, Object>> deleteSelectedAssetRequest(@RequestBody Map<String, List<Integer>> request) {
    try {
      List<Integer> ids = request.get("ids");
      assetreqservice.deleteSelected(ids);

      Map<String, Object> successResponse = new HashMap<>();
      successResponse.put("status", true);
      successResponse.put("message", "Selected asset requests deleted successfully!");

      return ResponseEntity.status(HttpStatus.OK).body(successResponse);
    } catch (Exception e) {
      return handleException(e);
    }
  }
  @PutMapping("/updateAssetRequest")
  public ResponseEntity<Map<String, Object>> updateAssetRequest(@RequestBody AssetRequestModel updatedAssetRequests) {
    try {
      assetreqservice.updateAssetRequests(updatedAssetRequests);
      Map<String, Object> successResponse = new HashMap<>();
      successResponse.put("status", true);
      successResponse.put("message", "Asset Request updated successfully!");
      return ResponseEntity.status(HttpStatus.OK).body(successResponse);
    } catch (Exception e) {
      return handleException(e);
    }
  }

    //    pullback tyare jyare pending status hoi
    @PutMapping("AssetRequestemployee/{AssetRequestsID}")
    public ResponseEntity<?> updateStatusToPullBack(@PathVariable Integer AssetRequestsID , Principal principal){
        try {
            System.out.println("in pullback");
            Optional<AssetRequestModel> assetRequestModel = assetreqservice.updateAssetRequestToPullBack(AssetRequestsID, principal);
            Map<String, Object> successResponse = new HashMap<>();
            successResponse.put("status", true);
            successResponse.put("message", "Asset request is pullback successfully!");
            successResponse.put("serviceResponse", assetRequestModel);
            return ResponseEntity.status(HttpStatus.CREATED).body(successResponse);
        } catch (Exception e) {
            return handleException(e);
        }
    }
    //    employee cancel karse when asset request manager approve

//    manager approve tyare karse jyare asset request pending ma hase
@PutMapping("AssetRequestManager/{AssetRequestsID}")
public ResponseEntity<?> updateStatusToApproved(@PathVariable Integer AssetRequestsID , Principal principal){
    try {
        System.out.println("in approved");
        Optional<AssetRequestModel> assetRequestModel = assetreqservice.updateAssetRequestToApproved(AssetRequestsID, principal);
        Map<String, Object> successResponse = new HashMap<>();
        successResponse.put("status", true);
        successResponse.put("message", "Asset request is approved successfully!");
        successResponse.put("serviceResponse", assetRequestModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(successResponse);
    } catch (Exception e) {
        return handleException(e);
    }
}
//      same for reject

//    owner accept or reject tyare karse jyare request approved ma hoi

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleException(@NotNull Exception e) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("status", false);
        errorResponse.put("error", e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}
