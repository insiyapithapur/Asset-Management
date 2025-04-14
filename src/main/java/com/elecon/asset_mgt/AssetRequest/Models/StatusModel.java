package com.elecon.asset_mgt.AssetRequest.Models;

import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "StatusModel")
public class StatusModel {

    @Id
    @GeneratedValue
    private Integer id;
    private String status;
}
