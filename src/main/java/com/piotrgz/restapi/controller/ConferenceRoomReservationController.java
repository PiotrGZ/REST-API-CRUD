package com.piotrgz.restapi.controller;


import com.piotrgz.restapi.model.ConferenceRoom;
import com.piotrgz.restapi.model.ConferenceRoomReservation;
import com.piotrgz.restapi.model.Organization;
import com.piotrgz.restapi.service.ConferenceRoomReservationService;
import com.piotrgz.restapi.service.ConferenceRoomService;
import com.piotrgz.restapi.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/conferenceroomreservation")
public class ConferenceRoomReservationController {


    private ConferenceRoomReservationService conferenceRoomReservationService;
    private ConferenceRoomService conferenceRoomService;
    private OrganizationService organizationService;

    @Autowired
    public ConferenceRoomReservationController(ConferenceRoomReservationService conferenceRoomReservationService, ConferenceRoomService conferenceRoomService, OrganizationService organizationService) {
        this.conferenceRoomReservationService = Objects.requireNonNull(conferenceRoomReservationService);
        this.organizationService = Objects.requireNonNull(organizationService);
        this.conferenceRoomService = Objects.requireNonNull(conferenceRoomService);

    }

    @PostMapping
    public ResponseEntity save(@Valid @RequestBody ConferenceRoomReservation conferenceRoomReservation) {

        if (conferenceRoomReservation.getConferenceRoom() != null || conferenceRoomReservation.getOrganization() != null) {
            return ResponseEntity.badRequest().body("Please pick existing Organization and Conference room rather than posting new one while creating Conference room reservation");
        }

        if (isConferenceRoomPresent(conferenceRoomReservation.getConferenceRoomName()) && isOrganizationPresent(conferenceRoomReservation.getOrganizationName())) {

            addReservationsToOrganizationAndRoomLists(conferenceRoomReservation);

            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().body("Please enter proper ID for both Organization and Conference room to Create Conference room reservation");
        }
    }


    @GetMapping

    public ResponseEntity<List<ConferenceRoomReservation>> getAll() {
        return ResponseEntity.ok(conferenceRoomReservationService.getAll());
    }

    @DeleteMapping
    public ResponseEntity delete(@RequestParam int id) {

        if (isConferenceRoomReservationPresent(id)) {

            removeReservationFromOrganizationAndRoomLists(id);

            conferenceRoomReservationService.delete(id);
            return ResponseEntity.ok().build();
        } else
            return ResponseEntity.badRequest().body("Conference room reservation with ID: " + id + " has not been found");
    }


    @PatchMapping
    public ResponseEntity update(@RequestParam int id, @RequestBody ConferenceRoomReservation conferenceRoomReservation) {
        if (conferenceRoomReservation.getConferenceRoomName() != null || conferenceRoomReservation.getOrganizationName() != null) {
            return ResponseEntity.badRequest().body("Only start-date and end-date can be updated. Please delete existing reservation and create new one if you wish to select other room or organization");
        }

        if (isConferenceRoomReservationPresent(id)) {
            conferenceRoomReservationService.update(id, conferenceRoomReservation);
            return ResponseEntity.ok().build();
        } else
            return ResponseEntity.badRequest().body("Conference room reservation with ID: " + id + " doesn't exist");
    }



    private boolean isConferenceRoomPresent(String name) {
        return conferenceRoomService.getAll().stream().anyMatch(t -> t.getName().equals(name));
    }

    private boolean isOrganizationPresent(String name) {
        return organizationService.getAll().stream().anyMatch(t -> t.getName().equals(name));
    }

    private boolean isConferenceRoomReservationPresent(int id) {
        return conferenceRoomReservationService.getAll().stream().anyMatch(t -> ((Integer) t.getId()).equals(id));
    }

    private void removeReservationFromOrganizationAndRoomLists(int id) {
        ConferenceRoomReservation conferenceRoomReservation = conferenceRoomReservationService.getAll()
                .stream()
                .filter(t -> t.getId() == id).findAny()
                .get();

        Organization organization = conferenceRoomReservation.getOrganization();
        organization.getConferenceRoomReservationCollection().remove(conferenceRoomReservation);

        ConferenceRoom conferenceRoom = conferenceRoomReservation.getConferenceRoom();
        conferenceRoom.getConferenceRoomReservationCollection().remove(conferenceRoomReservation);

        organizationService.save(organization);
        conferenceRoomService.save(conferenceRoom);
    }

    private void addReservationsToOrganizationAndRoomLists(ConferenceRoomReservation conferenceRoomReservation) {
        Organization organization = organizationService.findByName(conferenceRoomReservation.getOrganizationName());
        ConferenceRoom conferenceRoom = conferenceRoomService.findByName(conferenceRoomReservation.getConferenceRoomName());

        conferenceRoomReservation.setOrganization(organization);
        conferenceRoomReservation.setConferenceRoom(conferenceRoom);

        organization.getConferenceRoomReservationCollection().add(conferenceRoomReservation);
        conferenceRoom.getConferenceRoomReservationCollection().add(conferenceRoomReservation);

        conferenceRoomReservationService.save(conferenceRoomReservation);
        conferenceRoomService.save(conferenceRoom);
        organizationService.save(organization);
    }
}
