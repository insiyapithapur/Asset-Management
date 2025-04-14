package com.elecon.asset_mgt.Category.Service;
import com.elecon.asset_mgt.Category.Models.CategoryModel;
import com.elecon.asset_mgt.Category.Repository.CategoryRepo;
import com.elecon.asset_mgt.Category.dao.updateStatusDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService  {

  @Autowired private CategoryRepo repo;
  public void save(CategoryModel categoryModel){
    repo.save(categoryModel);
  }
  public void updateStatus(updateStatusDao updatestatusdao){
    Integer categoryId = updatestatusdao.getId();
    Optional<CategoryModel> optionalCategory = repo.findById(categoryId);

    if (optionalCategory.isPresent()) {
      CategoryModel categoryModel = optionalCategory.get();
      categoryModel.setStatus(updatestatusdao.getStatus());
      repo.save(categoryModel);
      System.out.println("Category status updated successfully!");
    } else {
      System.out.println("Category with ID " + categoryId + " not found.");
    }
  }
  public List<CategoryModel> getAll(){
    return repo.findAll();
  }
  public Optional<CategoryModel> findById(Integer id){
    return repo.findById(id);
  }
  public void deleteById(Integer id){
    repo.deleteById(id);
  }
  public void deleteSelected(List<Integer> ids) {
    List<CategoryModel> categoriesToDelete = repo.findAllById(ids);

    for (CategoryModel category : categoriesToDelete) {
      if (category != null) {
        repo.delete(category);
      }
    }
  }
  public void updateCategory(CategoryModel updatedCategory) {
    Integer categoryId = updatedCategory.getId();
    CategoryModel existingCategory = repo.findById(categoryId)
      .orElseThrow(() -> new CategoryNotFoundException("Category not found with ID: " + categoryId));
    existingCategory.setCategory_name(updatedCategory.getCategory_name());
    existingCategory.setCategory_desc(updatedCategory.getCategory_desc());
    existingCategory.setStatus(updatedCategory.isStatus());
    repo.save(existingCategory);
  }
}
