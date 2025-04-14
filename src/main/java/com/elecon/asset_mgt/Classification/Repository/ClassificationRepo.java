package com.elecon.asset_mgt.Classification.Repository;

import com.elecon.asset_mgt.Classification.Models.ClassificationModel;
import com.elecon.asset_mgt.location.Models.LocationModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ClassificationRepo extends JpaRepository<ClassificationModel,Integer> {
  @Query("SELECT c FROM ClassificationModel c WHERE c.location = :location")
  List<ClassificationModel> findByLocation(@Param("location") LocationModel location);
}
