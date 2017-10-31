package com.linkedin.learning.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * The type Room entity.
 */
@Entity
@Table(name = "Room")
public class RoomEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private Integer roomNumber;

    @NotNull
    private String price;

    /**
     * Instantiates a new Room entity.
     */
    public RoomEntity() {
    }

    /**
     * Instantiates a new Room entity.
     *
     * @param roomNumber the room number
     * @param price      the price
     */
    public RoomEntity(Integer roomNumber, String price) {
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
    public String getPrice() {
        return price;
    }

    /**
     * Sets price.
     *
     * @param price the price
     */
    public void setPrice(String price) {
        this.price = price;
    }
}
