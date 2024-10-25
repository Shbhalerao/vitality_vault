package com.fitnessapp.VitalityVault.domain.entities;

import jakarta.persistence.*;
import lombok.*;
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
public class Trainer {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "trainer_sq")
    private Long id;

    private String trainerName;

    private String trainerId;

    private Date workingSince;

    private List<Long> certifications;

    private String contactNo;

    private String emailId;

    private Date createdDate;

    private Boolean deactivated =  Boolean.FALSE;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "center_id")
    private FitnessCenter fitnessCenter;

    // Identifiers for linking to the Address entity
    private Long addressId; // Points to the Address table

}
