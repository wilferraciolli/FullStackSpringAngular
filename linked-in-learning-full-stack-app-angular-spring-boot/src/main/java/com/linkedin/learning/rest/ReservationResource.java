package com.linkedin.learning.rest;

import com.linkedin.learning.entity.RoomEntity;
import com.linkedin.learning.model.request.ReservationRequest;
import com.linkedin.learning.model.response.ReservationResponse;
import com.linkedin.learning.repository.PageableRoomRepository;
import com.linkedin.learning.repository.RoomRepository;
import converter.RoomEntityToReservationResponseConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

import static com.linkedin.learning.rest.ResourceConstants.ROOM_RESERVATION_V1;

/**
 * The type Reservation resource.
 */
@RestController
@RequestMapping(ROOM_RESERVATION_V1)
public class ReservationResource {

    @Autowired
    PageableRoomRepository pageableRoomRepository;

    @Autowired
    RoomRepository roomRepository;

    /**
     * Gets available rooms.
     *
     * @param checkin  the checkin
     * @param checkout the checkout
     * @return the available rooms
     */
    @RequestMapping(path = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Page<ReservationResponse> getAvailableRooms(
            @RequestParam(value = "checkin")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                    LocalDate checkin,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            @RequestParam(value = "checkout")
                    LocalDate checkout, Pageable pageable) {

        Page<RoomEntity> roomEntityList = pageableRoomRepository.findAll(pageable);

        return roomEntityList.map(new RoomEntityToReservationResponseConverter());
    }

    @RequestMapping(path = "/{roomId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<RoomEntity> getRoomById(
            @PathVariable
                    Long roomId) {
                RoomEntity result = roomRepository.findById(roomId);
                return new ResponseEntity<RoomEntity>(result, HttpStatus.OK);
    }

    /**
     * Create reservation response entity.
     *
     * @param reservationRequest the reservation request
     * @return the response entity
     */
    @RequestMapping(path = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ReservationResponse> createReservation(
            @RequestBody ReservationRequest reservationRequest) {

        return new ResponseEntity<>(new ReservationResponse(), HttpStatus.CREATED);
    }

    /**
     * Update reservation response entity.
     *
     * @param reservationRequest the reservation request
     * @return the response entity
     */
    @RequestMapping(path = "", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ReservationResponse> updateReservation(
            @RequestBody ReservationRequest reservationRequest) {

        return new ResponseEntity<>(new ReservationResponse(), HttpStatus.OK);
    }

    @RequestMapping(path = "{reservationId}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteReservation(
            @PathVariable
                    long reservationId) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
