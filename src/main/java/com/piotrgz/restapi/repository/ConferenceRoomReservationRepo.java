package com.piotrgz.restapi.repository;

import com.piotrgz.restapi.model.ConferenceRoomReservation;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ConferenceRoomReservationRepo extends CrudRepository<ConferenceRoomReservation, Integer> {
    List<ConferenceRoomReservation> findAll();
}
