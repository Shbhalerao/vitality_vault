package com.fitnessapp.VitalityVault.domain.entities;

import com.fitnessapp.VitalityVault.domain.dto.TrainerDto;
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

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name = "client_details")
@SQLDelete(sql = "UPDATE client_details SET deactivated = true WHERE id = ?")
@FilterDef(name = "deactivateClientFilter", parameters = @ParamDef(name = "isDeactivated", type = Boolean.class))
@Filter(name = "deactivateClientFilter", condition = "deactivated = :isDeactivated")
public class ClientEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "client_sq")
    private Long id;

    private String clientId;

    private String firstName;

    private String lastName;

    private Date dateOfBirth;

    private Double height;

    private Double weight;

    private String address;

    private Long city;

    private Integer state;

    private Long pinCode;

    private String contactNo;

    private String emailId;

    private Integer fitnessGoal;

    private Date createdDate;

    private boolean deactivated = Boolean.FALSE;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "trainer_id")
    private TrainerDto trainerDto;
}
