package com.witech.dataexplore.graphql.addresses;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import org.hibernate.annotations.Immutable;

@Entity
@Table(name = "address_view")
@Immutable
public class AddressView {
    @Id
    private UUID id;

    @Column(nullable = false, unique = true)
    private String city;

    @Column(nullable = false)
    private String street;

    public UUID getId() {
        return id;
    }

    public String getCity() {
        return city;
    }

    public String getStreet() {
        return street;
    }
}
