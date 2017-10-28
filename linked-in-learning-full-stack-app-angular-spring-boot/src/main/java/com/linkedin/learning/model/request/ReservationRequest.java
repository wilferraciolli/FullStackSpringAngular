package com.linkedin.learning.model.request;

import com.linkedin.learning.model.Links;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

/**
 * The type Reservation request.
 */
public class ReservationRequest {

    private Long id;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate checkin;
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
     * @param id       the id
     * @param checkin  the checkin
     * @param checkout the checkout
     */
    public ReservationRequest(Long id, LocalDate checkin, LocalDate checkout) {
        super();
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
