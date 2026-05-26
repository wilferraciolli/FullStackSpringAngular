package com.witech.dataexplore.graphql.addresses;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AddressViewRepository extends JpaRepository<AddressView, UUID>,
                                                JpaSpecificationExecutor<AddressView> {
}