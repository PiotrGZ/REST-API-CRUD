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

//    public ResponseEntity save(Reservation reservation) {
//
//        if (validateReservation(reservation)) {
//            reservationRepo.save(reservation);
//            return ResponseEntity.ok(reservation);
//        }
//
//        return reservationNotValid(validationOfReservationResult);
//    }
//
//    private boolean validateReservation(Reservation reservation) {
//
//        boolean validationFlag = true;
//
//        ResponseEntity organizationToSave = organizationService.findByName(reservation.getOrganizationName());
//        ResponseEntity conferenceRoomToSave = conferenceRoomService.findByName(reservation.getConferenceRoomName());
//        Optional<Reservation> reservationOptional = reservationRepo.findByName(reservation.getName());
//
//        if (!(organizationToSave.getBody() instanceof Organization)) {
//            validationOfReservationResult.append("Organization with name " + reservation.getOrganizationName() + " not found!\n");
//            validationFlag = false;
//        }
//        if (!(conferenceRoomToSave.getBody() instanceof ConferenceRoom)) {
//            validationOfReservationResult.append("Conference room with name " + reservation.getConferenceRoomName() + " not found!\n");
//            validationFlag = false;
//        }
//        if (reservationOptional.isPresent()) {
//            validationOfReservationResult.append("Reservation with name " + reservation.getOrganizationName() + " already exists!\n");
//            validationFlag = false;
//        }
//
//        return validationFlag;
//    }
//
//
//    public List<Reservation> getAll() {
//        return reservationRepo.findAll();
//    }
//
//    public ResponseEntity findByName(String name) {
//        Optional<Reservation> reservationOptional = findByNameInService(name);
//
//        if (reservationOptional.isPresent()) {
//            return ResponseEntity.ok(reservationOptional.get());
//        }
//        return badRequestNameNotFound(name);
//    }
//
//    public ResponseEntity delete(String name) {
//
//        Optional<Reservation> reservationToDelete = findByNameInService(name);
//
//        if (reservationToDelete.isPresent()) {
//            reservationRepo.delete(reservationToDelete.get());
//            return ResponseEntity.ok("Reservation " + name + " has been deleted");
//        }
//        return badRequestNameNotFound(name);
//    }
//
//    public ResponseEntity update(String name, Reservation reservation) {
//
//        Optional<Reservation> reservationUpdateOptional = findByNameInService(name);
//
//        if (reservationUpdateOptional.isPresent() && areNamesEqual(name, reservationUpdateOptional)) {
//            return badRequestNameIsNotUnique(name);
//        }
//
//        if (reservationUpdateOptional.isPresent()) {
//            Reservation reservationToUpdate = reservationUpdateOptional.get();
//
//            reservationToUpdate.setName(reservation.getName());
//            reservationToUpdate.setConferenceRoomName(reservation.getConferenceRoomName());
//            reservationToUpdate.setOrganizationName(reservation.getOrganizationName());
//            reservationToUpdate.setEndDate(reservation.getEndDate());
//            reservationToUpdate.setStartDate(reservation.getStartDate());
//
//            reservationRepo.save(reservationToUpdate);
//            return ResponseEntity.ok("Conference room " + name + " has been updated");
//        }
//        return badRequestNameNotFound(name);
//    }
//
//    private ResponseEntity badRequestNameNotFound(String name) {
//        return ResponseEntity.badRequest().body("Conference room " + name + " has not been found");
//    }
//
//    private Optional<Reservation> findByNameInService(String name) {
//        return reservationRepo.findByName(name);
//    }
//
//    private ResponseEntity reservationNotValid(StringBuilder message) {
//        validationOfReservationResult=new StringBuilder();
//        return ResponseEntity.badRequest().body(message.toString());
//    }
//    private boolean areNamesEqual(String name, Optional<Reservation> reservationOptional) {
//        return reservationOptional.get().getName().equals(name);
//    }
//    private ResponseEntity badRequestNameIsNotUnique(String name) {
//        return ResponseEntity.badRequest().body("Reservation with name " + name + " already exists!\n");
//    }
}
