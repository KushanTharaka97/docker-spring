package com.frankmoley.lil.learningspring.business.service;

import com.frankmoley.lil.learningspring.business.domain.RoomReservation;
import com.frankmoley.lil.learningspring.data.entity.Guest;
import com.frankmoley.lil.learningspring.data.entity.Reservation;
import com.frankmoley.lil.learningspring.data.entity.Room;
import com.frankmoley.lil.learningspring.data.repository.GuestRepository;
import com.frankmoley.lil.learningspring.data.repository.ReservationRepository;
import com.frankmoley.lil.learningspring.data.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class
ReservationService {
    //import the Crud Extended repositories for the @Entities
    private final RoomRepository roomRepository;
    private final GuestRepository guestRepository;
    private final ReservationRepository reservationRepository;

    @Autowired
    public ReservationService(RoomRepository roomRepository, GuestRepository guestRepository, ReservationRepository reservationRepository) {
        this.roomRepository = roomRepository;
        this.guestRepository = guestRepository;
        this.reservationRepository = reservationRepository;
    }

    public List<RoomReservation> getRoomReservationsForDate(Date date){
            //select bulk of rooms from the room table/Entity
            Iterable<Room> rooms = this.roomRepository.findAll();
            //hash map will contain the roomId and the roomReservations Details
            Map<Long, RoomReservation> roomReservationMap = new HashMap<>();
            rooms.forEach(room ->{
                RoomReservation roomReservation = new RoomReservation();
                roomReservation.setRoomId(room.getRoomId());
                roomReservation.setRoomName(room.getRoomName());
                roomReservation.setRoomNumber(room.getRoomNumber());
                roomReservationMap.put(room.getRoomId(), roomReservation);
            } );
            //set date to the find the reservations on the date, the method findReservationByReservationDate is implemented on the repository interface
            Iterable<Reservation> reservations = this.reservationRepository.findReservationByReservationDate(new java.sql.Date(date.getTime()));
            reservations.forEach(reservation -> {
                RoomReservation roomReservation = roomReservationMap.get(reservation.getRoomId());
                roomReservation.setDate(date);
                Guest guest = this.guestRepository.findById(reservation.getGuestId()).get();
                roomReservation.setFirstName(guest.getFirstName());
                roomReservation.setLastName(guest.getLastName());
                roomReservation.setGuestId(guest.getGuestId());
            });

            List <RoomReservation> roomReservations = new ArrayList<>();
            //get into a arraylist the room reservation values from the roomReservationMap
            for (Long id: roomReservationMap.keySet()){
                roomReservations.add(roomReservationMap.get(id));

            }
        return roomReservations;
    }
}
