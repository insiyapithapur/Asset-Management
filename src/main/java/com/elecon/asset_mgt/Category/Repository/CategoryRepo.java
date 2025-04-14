package com.elecon.asset_mgt.Category.Repository;


import com.elecon.asset_mgt.Category.Models.CategoryModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepo extends JpaRepository<CategoryModel,Integer> {

}
