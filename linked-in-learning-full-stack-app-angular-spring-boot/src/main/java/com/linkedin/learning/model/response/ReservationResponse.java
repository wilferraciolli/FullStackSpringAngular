package com.linkedin.learning.model.response;

import java.time.LocalDate;

/**
 * The type Reservation response.
 */
public class ReservationResponse {

    private Long id;
    private LocalDate checkin;
    private LocalDate checkout;

    /**
     * Instantiates a new Reservation response.
     */
    public ReservationResponse() {
    }

    /**
     * Instantiates a new Reservation response.
     *
     * @param id       the id
     * @param checkin  the checkin
     * @param checkout the checkout
     */
    public ReservationResponse(Long id, LocalDate checkin, LocalDate checkout) {
        this.id = id;
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
}
