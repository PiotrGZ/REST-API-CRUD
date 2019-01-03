package com.piotrgz.restapi.service;

import com.piotrgz.restapi.model.ConferenceRoom;
import com.piotrgz.restapi.model.Organization;
import com.piotrgz.restapi.repository.ConferenceRoomRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@Service
public class ConferenceRoomService {

    private ConferenceRoomRepo conferenceRoomRepo;

    @Autowired
    public ConferenceRoomService(ConferenceRoomRepo conferenceRoomRepo) {
        this.conferenceRoomRepo = Objects.requireNonNull(conferenceRoomRepo);
    }


    public void save(ConferenceRoom conferenceRoom) {
        conferenceRoomRepo.save(conferenceRoom);
    }

    public List<ConferenceRoom> getAll() {
        return conferenceRoomRepo.findAll();
    }

    public void update(int id, ConferenceRoom conferenceRoom) {
        ConferenceRoom toUpdate = conferenceRoomRepo.findById(id).get();
        toUpdate.setName(conferenceRoom.getName());
        toUpdate.setConferenceRoomReservationCollection(conferenceRoom.getConferenceRoomReservationCollection());
        toUpdate.setFloor(conferenceRoom.getFloor());
        toUpdate.setAvailable(conferenceRoom.isAvailable());
        toUpdate.setNumberOfSeats(conferenceRoom.getNumberOfSeats());

        conferenceRoomRepo.save(toUpdate);
    }

    public void delete(int id) {
        conferenceRoomRepo.deleteById(id);
    }
}
