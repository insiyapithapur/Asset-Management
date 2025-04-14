package com.elecon.asset_mgt.Tokens.Repository;

import com.elecon.asset_mgt.Tokens.Model.TokenModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepo extends JpaRepository<TokenModel,Integer>{
}

