package com.wiltech.todos.cars;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "car")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private String maker;

    @NotEmpty
    private String model;

    @Enumerated(EnumType.STRING)
    private CarFuelType fuelType;

    @Enumerated(EnumType.STRING)
    private CarTransmissionType transmissionType;

    private LocalDate purchaseDate;

    private Boolean carOwner;

    @Enumerated(EnumType.STRING)
    private CarOwnerType ownerType;

    @Enumerated(EnumType.STRING)
    private CarOwnerType registeredKeeperType;
}
