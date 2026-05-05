package com.witech.dataexplore.graphql.person;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface PersonViewRepository extends JpaRepository<PersonView, UUID> {
}