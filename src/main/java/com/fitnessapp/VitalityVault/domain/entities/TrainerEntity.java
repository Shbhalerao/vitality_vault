package com.fitnessapp.VitalityVault.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import org.hibernate.annotations.SQLDelete;

import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name = "trainer_details")
@SQLDelete(sql = "UPDATE trainer_details SET deactivated = true WHERE id = ?")
@FilterDef(name = "deactivateTrainerFilter", parameters = @ParamDef(name = "isDeactivated", type = Boolean.class))
@Filter(name = "deactivateTrainerFilter", condition = "deactivated = :isDeactivated")
public class TrainerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "trainer_sq")
    private Long id;

    private String trainerName;

    private Date workingSince;

    private List<Long> certifications;

    private Long city;

    private Long pinCode;

    private Integer state;

    private Long country;

    private String address;

    private String contactNo;

    private String emailId;

    private Date createdDate;

    private Boolean deactivated =  Boolean.FALSE;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id")
    private FitnessCenterEntity fitnessCenterEntity;

}
