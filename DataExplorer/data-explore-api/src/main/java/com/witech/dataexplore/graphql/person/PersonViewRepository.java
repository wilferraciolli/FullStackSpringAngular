package com.witech.dataexplore.graphql.person;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

// JpaSpecificationExecutor enables findAll(Specification<T>, Sort)
// which lets Spring translate filter objects into SQL WHERE clauses.
public interface PersonViewRepository extends JpaRepository<PersonView, UUID>,
                                               JpaSpecificationExecutor<PersonView> {
}