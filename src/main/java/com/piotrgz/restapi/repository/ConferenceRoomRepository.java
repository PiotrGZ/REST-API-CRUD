package com.piotrgz.restapi.repository;

import com.piotrgz.restapi.entity.ConferenceRoom;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConferenceRoomRepository extends CrudRepository<ConferenceRoom, String> {
}
