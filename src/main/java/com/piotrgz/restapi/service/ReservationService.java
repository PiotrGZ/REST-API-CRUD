package com.piotrgz.restapi.service;

import com.piotrgz.restapi.model.ConferenceRoom;
import com.piotrgz.restapi.model.Organization;
import com.piotrgz.restapi.model.Reservation;
import com.piotrgz.restapi.repository.ReservationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ReservationService {

    private ReservationRepo reservationRepo;
    private OrganizationService organizationService;
    private ConferenceRoomService conferenceRoomService;


    @Autowired
    public ReservationService(ReservationRepo reservationRepo, OrganizationService organizationService, ConferenceRoomService conferenceRoomService) {
        this.reservationRepo = Objects.requireNonNull(reservationRepo);
        this.organizationService = Objects.requireNonNull(organizationService);
        this.conferenceRoomService = Objects.requireNonNull(conferenceRoomService);
    }

    public Reservation save(Reservation reservation) throws MyValidationException {

        if (reservationRepo.findById(reservation.getName()).isPresent()) {
            throw new MyValidationException("Reservation with name " + reservation.getName() + " already exists!");
        }

        reservation.getOrganizationName().equals(organizationService.findByName(reservation.getOrganizationName()));
        reservation.getConferenceRoomName().equals(conferenceRoomService.findByName(reservation.getConferenceRoomName()));

        return reservationRepo.save(reservation);
    }

    public Iterable<Reservation> getAll() {
        return reservationRepo.findAll();
    }


    public Reservation findByName(String name) throws MyValidationException {
        return reservationRepo.findById(name).orElseThrow(() -> new MyValidationException("Reservation " + name + " has not been found"));
    }


    public Reservation update(String name, Reservation reservation) throws MyValidationException {

        if (reservationRepo.findById(reservation.getName()).isPresent()) {
            throw new MyValidationException("Reservation with name " + name + " already exists!");
        }

        reservation.getOrganizationName().equals(organizationService.findByName(reservation.getOrganizationName()));
        reservation.getConferenceRoomName().equals(conferenceRoomService.findByName(reservation.getConferenceRoomName()));
        Reservation reservationToUpdate = findByName(name);

        reservationToUpdate.setName(reservation.getName());
        reservationToUpdate.setStartDate(reservation.getStartDate());
        reservationToUpdate.setEndDate(reservation.getEndDate());
        reservationToUpdate.setOrganizationName(reservation.getOrganizationName());
        reservationToUpdate.setConferenceRoomName(reservation.getConferenceRoomName());

        return reservationRepo.save(reservationToUpdate);
    }


    public void delete(String name) throws MyValidationException {

        reservationRepo.delete(findByName(name));
    }
}
