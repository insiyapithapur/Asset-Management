package com.elecon.asset_mgt.location.Services;


import com.elecon.asset_mgt.location.Models.LocationModel;
import com.elecon.asset_mgt.location.Repository.LocationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LocationService {
  @Autowired
  private LocationRepo repo;
  public void save(LocationModel locationModel) {
    repo.save(locationModel);
  }
  public List<LocationModel> getAll() {return repo.findAll();}

  public Optional<LocationModel> findById(Integer id){
    return repo.findById(id);
  }
  public void deleteById(Integer id){
    repo.deleteById(id);
  }
  public void deleteSelected(List<Integer> ids){
    List<LocationModel> locationsToDelete = repo.findAllById(ids);
    for (LocationModel location : locationsToDelete){
      if( location != null ) {
        repo.delete(location);
      }
    }
  }
  public void updateLocation(LocationModel updateLocation){
    Integer locationId = updateLocation.getId();
    LocationModel existingLocation = repo.findById(locationId)
      .orElseThrow(() -> new LocationNotFoundException("Location not found with Id: " + locationId));
    existingLocation.setLocation_name(updateLocation.getLocation_name());
    existingLocation.setCompany_name(updateLocation.getCompany_name());
    existingLocation.setDepartment_name(updateLocation.getDepartment_name());
    existingLocation.setStatus(updateLocation.isStatus());
    repo.save(existingLocation);
  }
}
