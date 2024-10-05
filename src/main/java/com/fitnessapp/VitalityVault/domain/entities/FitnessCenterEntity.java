package com.fitnessapp.VitalityVault.domain.entities;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name = "fitness_center_details")
@SQLDelete(sql = "UPDATE fitness_center_details SET deactivated = true WHERE id = ?")
@FilterDef(name = "deactivatedCenterFilter", parameters = @ParamDef(name = "isDeactivated", type = Boolean.class))
@Filter(name = "deactivatedCenterFilter", condition = "deactivated = :isDeactivated")
public class FitnessCenterEntity {

    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "fitness_center_sq")
    @Id
    private Long id;

    private String centerId;

    private String centerName;

    private String locality;

    private String addressLine1;

    private String addressLine2;

    private Long city; //Picklist

    private Long pinCode;

    private Integer state; //PickList

    private String emailId;

    private String contactNo;

    private Date createdDate;

    private Boolean deactivated =  Boolean.FALSE;

}
