package com.piotrgz.restapi.repository;

import com.piotrgz.restapi.model.Reservation;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ReservationRepo extends CrudRepository<Reservation, Integer> {
    List<Reservation> findAll();
    Optional<Reservation> findByName(String name);
}
