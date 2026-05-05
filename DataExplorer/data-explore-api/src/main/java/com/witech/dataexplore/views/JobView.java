package com.witech.dataexplore.views;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import org.hibernate.annotations.Immutable;

@Entity
@Table(name = "job_view")
@Immutable
public class JobView {
    @Id
    private UUID id;

    private UUID personId;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    private String jobTitle;

    private UUID jobStartDate;

    private UUID jobEndDate;
}
