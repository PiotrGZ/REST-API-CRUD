package com.piotrgz.restapi.service;


import com.piotrgz.restapi.exceptions.MyEntityAlreadyExistsException;
import com.piotrgz.restapi.exceptions.MyEntityNotFoundException;
import com.piotrgz.restapi.entity.ConferenceRoom;
import com.piotrgz.restapi.repository.ConferenceRoomRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.Objects;


@Service
public class ConferenceRoomService {

    private ConferenceRoomRepo conferenceRoomRepo;

    @Autowired
    public ConferenceRoomService(ConferenceRoomRepo conferenceRoomRepo) {
        this.conferenceRoomRepo = Objects.requireNonNull(conferenceRoomRepo);
    }

    public ConferenceRoom save(ConferenceRoom conferenceRoom) throws IllegalArgumentException {

        if (conferenceRoomRepo.findById(conferenceRoom.getName()).isPresent()) {
            throw new MyEntityAlreadyExistsException("Conference room with name " + conferenceRoom.getName() + " already exists!");
        }
        return conferenceRoomRepo.save(conferenceRoom);
    }

    public Iterable<ConferenceRoom> getAll() {
        return conferenceRoomRepo.findAll();
    }


    public ConferenceRoom findByName(String name) throws IllegalArgumentException {
        return conferenceRoomRepo.findById(name).orElseThrow(() -> new MyEntityNotFoundException("Conference room " + name + " has not been found"));
    }


    public ConferenceRoom update(String name, ConferenceRoom conferenceRoom) throws IllegalArgumentException {

        if (conferenceRoomRepo.findById(conferenceRoom.getName()).isPresent()) {
            throw new MyEntityAlreadyExistsException("Conference room " + name + " already exists!");
        }

        ConferenceRoom conferenceRoomToUpdate = findByName(name);

        conferenceRoomToUpdate.setName(conferenceRoom.getName());
        conferenceRoomToUpdate.setNumberOfSeats(conferenceRoom.getNumberOfSeats());
        conferenceRoomToUpdate.setAvailable(conferenceRoom.isAvailable());
        conferenceRoomToUpdate.setFloor(conferenceRoom.getFloor());
        conferenceRoomToUpdate.setCommunicationInterface(conferenceRoom.getCommunicationInterface());
        conferenceRoomToUpdate.setExternalPhoneNumber(conferenceRoom.getExternalPhoneNumber());
        conferenceRoomToUpdate.setInternalPhoneNumber(conferenceRoom.getInternalPhoneNumber());
        conferenceRoomToUpdate.setProjector(conferenceRoom.getProjector());
        conferenceRoomToUpdate.setNumberOfLyingPlace(conferenceRoom.getNumberOfLyingPlace());
        conferenceRoomToUpdate.setNumberOfStandingPlace(conferenceRoom.getNumberOfStandingPlace());
        conferenceRoomToUpdate.setPhonePresent(conferenceRoom.isPhonePresent());

        return conferenceRoomRepo.save(conferenceRoomToUpdate);
    }


    public void delete(String name) throws IllegalArgumentException {

        conferenceRoomRepo.delete(findByName(name));
    }
}
