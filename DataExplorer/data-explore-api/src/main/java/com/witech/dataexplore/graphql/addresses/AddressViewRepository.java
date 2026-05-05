package com.witech.dataexplore.graphql.addresses;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressViewRepository extends JpaRepository<AddressView, UUID> {
}