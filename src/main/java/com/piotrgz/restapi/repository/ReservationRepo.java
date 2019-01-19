package com.piotrgz.restapi.repository;

import com.piotrgz.restapi.entity.Reservation;
import org.springframework.data.repository.CrudRepository;

public interface ReservationRepo extends CrudRepository<Reservation, String> {
}
