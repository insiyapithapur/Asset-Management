package com.elecon.asset_mgt.Category.dao;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class updateStatusDao {
    private Integer id;
    private boolean status;

    public boolean getStatus() {
        return status ;
    }
}
