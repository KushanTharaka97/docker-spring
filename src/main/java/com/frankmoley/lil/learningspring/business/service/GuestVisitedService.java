package com.frankmoley.lil.learningspring.business.service;

import com.frankmoley.lil.learningspring.business.domain.GuestVisited;
import com.frankmoley.lil.learningspring.business.domain.RoomReservation;
import com.frankmoley.lil.learningspring.data.entity.Guest;
import com.frankmoley.lil.learningspring.data.repository.GuestRepository;
import org.apache.catalina.LifecycleState;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GuestVisitedService {
private final GuestRepository guestRepository;

    public GuestVisitedService(GuestRepository guestRepository) {
        this.guestRepository = guestRepository;
    }
// in this I am going to list  all the guest in the system, all ever visited to our system
    public List<GuestVisited> showVisitedGuests(){
        Iterable<Guest> guests = this.guestRepository.findAll();
        Map<Long, GuestVisited> guestVIsitedMap = new HashMap<>();

        guests.forEach(guest -> {
            GuestVisited guestVisited = new GuestVisited();
            guestVisited.setLastName(guest.getLastName());
            guestVisited.setFirstName(guest.getFirstName());
            guestVisited.setEmailAddress(guest.getEmailAddress());
            guestVisited.setPhoneNumber(guest.getPhoneNumber());
            guestVIsitedMap.put(guest.getGuestId(),guestVisited);

        });

        List <GuestVisited> guestVisits = new ArrayList<>();
        for(long id: guestVIsitedMap.keySet()){
            guestVisits.add(guestVIsitedMap.get(id));
        }

        return guestVisits;
    }
}
