package com.elecon.asset_mgt.location.Repository;


import com.elecon.asset_mgt.location.Models.LocationModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepo extends JpaRepository<LocationModel,Integer> {
}
