package com.piotrgz.restapi.repository;

import com.piotrgz.restapi.entity.ConferenceRoom;
import org.springframework.data.repository.CrudRepository;

public interface ConferenceRoomRepo extends CrudRepository<ConferenceRoom, String> {
}
