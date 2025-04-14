package com.elecon.asset_mgt.Tokens.Services;

import com.elecon.asset_mgt.Tokens.Model.TokenModel;
import com.elecon.asset_mgt.Tokens.Repository.TokenRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TokenServices {
  @Autowired private TokenRepo repo;
  public void save(TokenModel tokenModel) {
    repo.save(tokenModel);
  }
  public List<TokenModel> getAll () {
    return repo.findAll();
  }
  public Optional<TokenModel> findById(Integer id){
    return repo.findById(id);
  }

}
