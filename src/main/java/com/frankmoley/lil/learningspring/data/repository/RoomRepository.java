package com.frankmoley.lil.learningspring.data.repository;

import com.frankmoley.lil.learningspring.data.entity.Reservation;
import com.frankmoley.lil.learningspring.data.entity.Room;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface RoomRepository extends CrudRepository<Room, Long> {

}
