package com.elecon.asset_mgt.Classification.Services;

import com.elecon.asset_mgt.Classification.Models.ClassificationModel;
import com.elecon.asset_mgt.Classification.Repository.ClassificationRepo;
import com.elecon.asset_mgt.location.Models.LocationModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClassificationService {
  @Autowired
  private ClassificationRepo repo;

  public void save(ClassificationModel classificationModel) {
    repo.save(classificationModel);
  }

  public List<ClassificationModel> getAll() {
    return repo.findAll();
  }

  public List<ClassificationModel> findByLocation(LocationModel location){
    return repo.findByLocation(location);
  }

  public Optional<ClassificationModel> findById(Integer id) {
    return repo.findById(id);
  }

  public void deleteById(Integer id) {
    repo.deleteById(id);
  }

  public void deleteSelected(List<Integer> ids) {
    List<ClassificationModel> classificationsToDelete = repo.findAllById(ids);
    for (ClassificationModel classification : classificationsToDelete) {
      if (classification != null) {
        repo.delete(classification);
      }
    }
  }

  public void updateClassification(ClassificationModel updatedClassification) {
    Integer classificationId = updatedClassification.getId();
    ClassificationModel existingClassification = repo.findById(classificationId)
      .orElseThrow(() -> new ClassificationNotFoundException("Classification not found with Id: " + classificationId));

    existingClassification.setStatus(updatedClassification.isStatus());
    existingClassification.setClass_name(updatedClassification.getClass_name());
    existingClassification.setClass_desc(updatedClassification.getClass_desc());
    existingClassification.setOwner(updatedClassification.getOwner());
    existingClassification.setLocation(updatedClassification.getLocation());
    existingClassification.setLocationOwner(updatedClassification.getLocationOwner());

    repo.save(existingClassification);
  }
}
