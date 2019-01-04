package com.piotrgz.restapi.service;

import com.piotrgz.restapi.model.ConferenceRoomReservation;
import com.piotrgz.restapi.repository.ConferenceRoomReservationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class ConferenceRoomReservationService {

    private ConferenceRoomReservationRepo conferenceRoomReservationRepo;

    @Autowired
    public ConferenceRoomReservationService(ConferenceRoomReservationRepo conferenceRoomReservationRepo) {
        this.conferenceRoomReservationRepo = Objects.requireNonNull(conferenceRoomReservationRepo);
    }


    public void save(ConferenceRoomReservation conferenceRoomReservation) {
        conferenceRoomReservationRepo.save(conferenceRoomReservation);
    }

    public List<ConferenceRoomReservation> getAll() {
        return conferenceRoomReservationRepo.findAll();
    }

    public void update(int id, ConferenceRoomReservation conferenceRoomReservation) {
        ConferenceRoomReservation toUpdate = conferenceRoomReservationRepo.findById(id);
//        toUpdate.setConferenceRoom(conferenceRoomReservation.getConferenceRoom());
//        toUpdate.setOrganization(conferenceRoomReservation.getOrganization());
        toUpdate.setStartDate(conferenceRoomReservation.getStartDate());
        toUpdate.setEndDate(conferenceRoomReservation.getEndDate());

        conferenceRoomReservationRepo.save(toUpdate);
    }

    public void delete(int id) {
        conferenceRoomReservationRepo.deleteById(id);
    }

    public ConferenceRoomReservation findById(int id){
        return findById(id);
    }
}
