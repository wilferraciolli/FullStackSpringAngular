package com.linkedin.learning.model.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.deser.std.DateDeserializers;
import com.linkedin.learning.model.Links;
import com.linkedin.learning.utils.LocalDateSerializer;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

/**
 * The type Reservation request.
 */
public class ReservationRequest {

    private Long id;

    private Long roomId;

    @JsonDeserialize(using = LocalDateSerializer.class)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate checkin;

    @JsonDeserialize(using = LocalDateSerializer.class)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate checkout;

    /**
     * Instantiates a new Reservation request.
     */
    public ReservationRequest() {
        super();
    }

    /**
     * Instantiates a new Reservation request.
     *
     * @param roomId   the room id
     * @param checkin  the checkin
     * @param checkout the checkout
     */
    public ReservationRequest(Long roomId, LocalDate checkin, LocalDate checkout) {
        this.roomId = roomId;
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
     * Gets room id.
     *
     * @return the room id
     */
    public Long getRoomId() {
        return roomId;
    }

    /**
     * Sets room id.
     *
     * @param roomId the room id
     */
    public void setRoomId(Long roomId) {
        this.roomId = roomId;
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
}
