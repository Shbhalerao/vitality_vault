package com.fitnessapp.VitalityVault.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "address")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long addressId;

    @Column(nullable = false)
    private String addressLine;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String state;

    @Column(nullable = false)
    private String country;

    @Column(nullable = false)
    private String pinCode;

    private String additionalInfo;

    // To identify the type of entity (Client, Trainer, Gym)
    @Column(nullable = false)
    private String entityType;

    // To store the ID of the corresponding entity (Client, Trainer, Gym)
    @Column(nullable = false)
    private Long entityId;
}
