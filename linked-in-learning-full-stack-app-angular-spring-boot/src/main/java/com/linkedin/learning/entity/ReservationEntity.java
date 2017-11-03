package com.linkedin.learning.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * The type Reservation entity. Each room amy have multiple reservations.
 */
@Entity
@Table(name = "Reservation")
public class ReservationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private LocalDate checkin;

    @NotNull
    private LocalDate checkout;

    @ManyToOne
    private RoomEntity roomEntity;

    /**
     * Instantiates a new Reservation entity.
     */
    public ReservationEntity() {
    }

    /**
     * Instantiates a new Reservation entity.
     *
     * @param checkin  the checkin
     * @param checkout the checkout
     */
    public ReservationEntity(LocalDate checkin, LocalDate checkout) {
        this.checkin = checkin;
        this.checkout = checkout;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets checkin.
     *
     * @return the checkin
     */
    public LocalDate getCheckin() {
        return checkin;
    }

    /**
     * Sets checkin.
     *
     * @param checkin the checkin
     */
    public void setCheckin(LocalDate checkin) {
        this.checkin = checkin;
    }

    /**
     * Gets checkout.
     *
     * @return the checkout
     */
    public LocalDate getCheckout() {
        return checkout;
    }

    /**
     * Sets checkout.
     *
     * @param checkout the checkout
     */
    public void setCheckout(LocalDate checkout) {
        this.checkout = checkout;
    }

    /**
     * Gets room entity.
     *
     * @return the room entity
     */
    public RoomEntity getRoomEntity() {
        return roomEntity;
    }

    /**
     * Sets room entity.
     *
     * @param roomEntity the room entity
     */
    public void setRoomEntity(RoomEntity roomEntity) {
        this.roomEntity = roomEntity;
    }
}
