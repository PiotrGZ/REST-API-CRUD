package com.piotrgz.restapi.repository;

import com.piotrgz.restapi.model.ConferenceRoom;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ConferenceRoomRepo extends CrudRepository<ConferenceRoom, Integer> {
    List<ConferenceRoom> findAll();
    Optional<ConferenceRoom> findByName(String name);
}
