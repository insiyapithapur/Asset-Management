package com.elecon.asset_mgt.Type.Repository;

import com.elecon.asset_mgt.Type.Models.TypeModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TypeRepo extends JpaRepository<TypeModel,Integer> {
}
