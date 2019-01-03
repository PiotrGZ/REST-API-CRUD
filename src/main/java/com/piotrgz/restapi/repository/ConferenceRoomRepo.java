package com.piotrgz.restapi.repository;

import com.piotrgz.restapi.model.ConferenceRoom;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ConferenceRoomRepo extends CrudRepository<ConferenceRoom, Integer> {
    List<ConferenceRoom> findAll();
}
