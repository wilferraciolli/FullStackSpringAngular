package com.linkedin.learning.rest;

import com.linkedin.learning.entity.ReservationEntity;
import com.linkedin.learning.entity.RoomEntity;
import com.linkedin.learning.model.request.ReservationRequest;
import com.linkedin.learning.model.response.ReservableRoomResponse;
import com.linkedin.learning.model.response.ReservationResponse;
import com.linkedin.learning.repository.PageableRoomRepository;
import com.linkedin.learning.repository.ReservationRepository;
import com.linkedin.learning.repository.RoomRepository;
import converter.RoomEntityToReservableRoomResponseConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
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
@CrossOrigin
public class ReservationResource {

    @Autowired
    PageableRoomRepository pageableRoomRepository;

    @Autowired
    RoomRepository roomRepository;

    @Autowired
    ReservationRepository reservationRepository;

    @Autowired
    ConversionService conversionService;

    /**
     * Gets available rooms.
     *
     * @param checkin  the checkin
     * @param checkout the checkout
     * @return the available rooms
     */
    @RequestMapping(path = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Page<ReservableRoomResponse> getAvailableRooms(
            @RequestParam(value = "checkin")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                    LocalDate checkin,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            @RequestParam(value = "checkout")
                    LocalDate checkout, Pageable pageable) {

        Page<RoomEntity> roomEntityList = pageableRoomRepository.findAll(pageable);

        return roomEntityList.map(new RoomEntityToReservableRoomResponseConverter());
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

        ReservationEntity reservationEntity = conversionService.convert(reservationRequest, ReservationEntity.class);
        reservationRepository.save(reservationEntity);

        //find the room
        RoomEntity roomEntity = roomRepository.findById(reservationRequest.getRoomId());
        roomEntity.addReservationEntity(reservationEntity);
        roomRepository.save(roomEntity);

        reservationEntity.setRoomEntity(roomEntity);

        ReservationResponse reservationResponse = conversionService.convert(reservationEntity, ReservationResponse.class);

        return new ResponseEntity<>(reservationResponse, HttpStatus.CREATED);
    }

    /**
     * Update reservation response entity.
     *
     * @param reservationRequest the reservation request
     * @return the response entity
     */
    @RequestMapping(path = "", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ReservableRoomResponse> updateReservation(
            @RequestBody ReservationRequest reservationRequest) {

        return new ResponseEntity<>(new ReservableRoomResponse(), HttpStatus.OK);
    }

    @RequestMapping(path = "{reservationId}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteReservation(
            @PathVariable
                    long reservationId) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
