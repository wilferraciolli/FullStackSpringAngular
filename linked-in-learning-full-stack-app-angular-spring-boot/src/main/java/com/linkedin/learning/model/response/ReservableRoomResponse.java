package com.linkedin.learning.model.response;

import com.linkedin.learning.model.Links;

/**
 * The type Reservation response.
 */
public class ReservableRoomResponse {

    private Long id;
    private Integer roomNumber;
    private Integer price;
    private Links links;

    /**
     * Instantiates a new Reservation response.
     */
    public ReservableRoomResponse() {
        super();
    }

    /**
     * Instantiates a new Reservation response.
     *
     * @param roomNumber the room number
     * @param price      the price
     */
    public ReservableRoomResponse(Integer roomNumber, Integer price) {
        super();
        this.roomNumber = roomNumber;
        this.price = price;
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
     * Gets room number.
     *
     * @return the room number
     */
    public Integer getRoomNumber() {
        return roomNumber;
    }

    /**
     * Sets room number.
     *
     * @param roomNumber the room number
     */
    public void setRoomNumber(Integer roomNumber) {
        this.roomNumber = roomNumber;
    }

    /**
     * Gets price.
     *
     * @return the price
     */
    public Integer getPrice() {
        return price;
    }

    /**
     * Sets price.
     *
     * @param price the price
     */
    public void setPrice(Integer price) {
        this.price = price;
    }

    /**
     * Gets links.
     *
     * @return the links
     */
    public Links getLinks() {
        return links;
    }

    /**
     * Sets links.
     *
     * @param links the links
     */
    public void setLinks(Links links) {
        this.links = links;
    }
}
