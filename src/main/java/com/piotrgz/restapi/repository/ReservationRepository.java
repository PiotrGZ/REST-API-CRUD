package com.piotrgz.restapi.repository;

import com.piotrgz.restapi.entity.Reservation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends CrudRepository<Reservation, String> {
    List<Reservation> findAllByConferenceRoomName(String name);
}
