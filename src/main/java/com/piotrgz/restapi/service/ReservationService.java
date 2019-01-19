package com.piotrgz.restapi.service;


import com.piotrgz.restapi.exceptions.MyEntityAlreadyExistsException;
import com.piotrgz.restapi.exceptions.MyEntityNotFoundException;
import com.piotrgz.restapi.entity.Reservation;
import com.piotrgz.restapi.repository.ReservationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

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

    public Reservation save(Reservation reservation) throws IllegalArgumentException {

        if (reservationRepo.findById(reservation.getName()).isPresent()) {
            throw new MyEntityAlreadyExistsException("Reservation with name " + reservation.getName() + " already exists!");
        }

        reservation.getOrganizationName().equals(organizationService.findByName(reservation.getOrganizationName()));
        reservation.getConferenceRoomName().equals(conferenceRoomService.findByName(reservation.getConferenceRoomName()));

        return reservationRepo.save(reservation);
    }

    public Iterable<Reservation> getAll() {
        return reservationRepo.findAll();
    }


    public Reservation findByName(String name) throws IllegalArgumentException {
        return reservationRepo.findById(name).orElseThrow(() -> new MyEntityNotFoundException("Reservation " + name + " has not been found"));
    }


    public Reservation update(String name, Reservation reservation) throws IllegalArgumentException {

        if (reservationRepo.findById(reservation.getName()).isPresent()) {
            throw new MyEntityAlreadyExistsException("Reservation with name " + name + " already exists!");
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


    public void delete(String name) throws IllegalArgumentException {

        reservationRepo.delete(findByName(name));
    }
}
