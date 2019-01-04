package com.piotrgz.restapi.controller;


import com.piotrgz.restapi.model.ConferenceRoom;
import com.piotrgz.restapi.model.ConferenceRoomReservation;
import com.piotrgz.restapi.model.Organization;
import com.piotrgz.restapi.service.ConferenceRoomReservationService;
import com.piotrgz.restapi.service.ConferenceRoomService;
import com.piotrgz.restapi.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    public ResponseEntity save(@Valid @RequestBody ConferenceRoomReservation conferenceRoomReservation, @RequestParam int organizationID, @RequestParam int conferenceRoomID) {

        if(conferenceRoomReservation.getConferenceRoom()!=null || conferenceRoomReservation.getOrganization()!=null){
            return ResponseEntity.badRequest().body("Please pick existing Organization and Conference room rather than posting new one while creating Conference room reservation");
        }

        if (isConferenceRoomPresent(conferenceRoomID) && isOrganizationPresent(organizationID)) {

            Organization organization=organizationService.findById(organizationID);
            ConferenceRoom conferenceRoom=conferenceRoomService.findById(conferenceRoomID);

            conferenceRoomReservation.setOrganization(organization);
            conferenceRoomReservation.setConferenceRoom(conferenceRoom);

            conferenceRoomReservationService.save(conferenceRoomReservation);

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
            conferenceRoomReservationService.delete(id);
            return ResponseEntity.ok().build();
        } else
            return ResponseEntity.badRequest().body("Conference room reservation with ID: " + id + " has not been found");
    }


    @PatchMapping
    public ResponseEntity update(@RequestParam int id, @Valid @RequestBody ConferenceRoomReservation conferenceRoomReservation) {
        if(conferenceRoomReservation.getConferenceRoom()!=null || conferenceRoomReservation.getOrganization()!=null){
            return ResponseEntity.badRequest().body("Please pick existing Organization and Conference room rather than posting new one while creating Conference room reservation");
        }
        if (isConferenceRoomReservationPresent(id)) {
            conferenceRoomReservationService.update(id, conferenceRoomReservation);
            return ResponseEntity.ok().build();
        } else
            return ResponseEntity.badRequest().body("Conference room reservation with ID: " + id + " doesn't exist");
    }


    private boolean isConferenceRoomPresent(int id) {
        return conferenceRoomService.getAll().stream().anyMatch(t -> ((Integer) t.getId()).equals(id));
    }

    private boolean isOrganizationPresent(int id) {
        return organizationService.getAll().stream().anyMatch(t -> ((Integer) t.getId()).equals(id));
    }

    private boolean isConferenceRoomReservationPresent(int id) {
        return organizationService.getAll().stream().anyMatch(t -> ((Integer) t.getId()).equals(id));
    }
}
