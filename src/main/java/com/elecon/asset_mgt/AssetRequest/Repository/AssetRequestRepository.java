package com.elecon.asset_mgt.AssetRequest.Repository;

import com.elecon.asset_mgt.AssetRequest.Models.AssetRequestModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssetRequestRepository extends JpaRepository<AssetRequestModel, Integer> {
}
