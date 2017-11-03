package com.linkedin.learning.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private List<ReservationEntity> reservationEntityList;

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
     * Add reservation entity.
     *
     * @param reservationEntity the reservation entity
     */
    public void addReservationEntity(ReservationEntity reservationEntity){
        if (null == reservationEntity){
            reservationEntityList = new ArrayList<>();
        }
        this.reservationEntityList.add(reservationEntity);
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

    /**
     * Gets reservation entity list.
     *
     * @return the reservation entity list
     */
    public List<ReservationEntity> getReservationEntityList() {
        return reservationEntityList;
    }

    /**
     * Sets reservation entity list.
     *
     * @param reservationEntityList the reservation entity list
     */
    public void setReservationEntityList(List<ReservationEntity> reservationEntityList) {
        this.reservationEntityList = reservationEntityList;
    }
}
