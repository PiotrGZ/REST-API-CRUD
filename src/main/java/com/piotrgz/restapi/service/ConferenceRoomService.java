package com.piotrgz.restapi.service;

import com.piotrgz.restapi.model.ConferenceRoom;
import com.piotrgz.restapi.repository.ConferenceRoomRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Service
public class ConferenceRoomService {

    private ConferenceRoomRepo conferenceRoomRepo;

    @Autowired
    public ConferenceRoomService(ConferenceRoomRepo conferenceRoomRepo) {
        this.conferenceRoomRepo = Objects.requireNonNull(conferenceRoomRepo);
    }

    public ResponseEntity save(ConferenceRoom conferenceRoom) {

        Optional<ConferenceRoom> conferenceRoomToSave = findByNameInService(conferenceRoom.getName());

        if (!conferenceRoomToSave.isPresent()) {
            conferenceRoomRepo.save(conferenceRoom);
            return ResponseEntity.ok(conferenceRoom);
        }
        return badRequestNameIsNotUnique(conferenceRoom.getName());
    }


    public List<ConferenceRoom> getAll() {
        return conferenceRoomRepo.findAll();
    }


    public ResponseEntity findByName(String name) {
        Optional<ConferenceRoom> conferenceRoomOptional = findByNameInService(name);

        if (conferenceRoomOptional.isPresent()) {
            return ResponseEntity.ok(conferenceRoomOptional.get());
        }
        return badRequestNameNotFound(name);
    }


    public ResponseEntity update(String name, ConferenceRoom conferenceRoom) {

        Optional<ConferenceRoom> conferenceRoomOptional = findByNameInService(name);

        if (conferenceRoomOptional.isPresent() && areNamesEqual(conferenceRoom, conferenceRoomOptional)) {
            return badRequestNameIsNotUnique(name);
        }


        if (conferenceRoomOptional.isPresent()) {
            ConferenceRoom conferenceRoomToUpdate = conferenceRoomOptional.get();
            conferenceRoomToUpdate.setName(conferenceRoom.getName());
            conferenceRoomRepo.save(conferenceRoomToUpdate);
            return ResponseEntity.ok("Conference room " + name + " has been updated");
        }
        return badRequestNameNotFound(name);
    }


    public ResponseEntity delete(String name) {

        Optional<ConferenceRoom> organizationToDelete = findByNameInService(name);

        if (organizationToDelete.isPresent()) {
            conferenceRoomRepo.delete(organizationToDelete.get());
            return ResponseEntity.ok("Conference room " + name + " has been deleted");
        }
        return badRequestNameNotFound(name);
    }

    private ResponseEntity badRequestNameNotFound(String name) {
        return ResponseEntity.badRequest().body("Conference room " + name + " has not been found");
    }

    private Optional<ConferenceRoom> findByNameInService(String name) {
        return conferenceRoomRepo.findByName(name);
    }

    private boolean areNamesEqual(ConferenceRoom organization, Optional<ConferenceRoom> organizationToUpdateOpt) {
        return organizationToUpdateOpt.get().getName().equals(organization.getName());
    }

    private ResponseEntity badRequestNameIsNotUnique(String name) {
        return ResponseEntity.badRequest().body("Conference room with name " + name + " already exists!\n");
    }
}
