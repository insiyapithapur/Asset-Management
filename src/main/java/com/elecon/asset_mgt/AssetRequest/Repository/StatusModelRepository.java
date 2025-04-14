package com.elecon.asset_mgt.AssetRequest.Repository;

import com.elecon.asset_mgt.AssetRequest.Models.StatusModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusModelRepository extends JpaRepository<StatusModel,Integer> {
    StatusModel findByStatus(String status);
}
