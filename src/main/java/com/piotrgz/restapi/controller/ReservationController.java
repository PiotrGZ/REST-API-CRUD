package com.piotrgz.restapi.controller;


import com.piotrgz.restapi.model.ConferenceRoom;
import com.piotrgz.restapi.model.Reservation;
import com.piotrgz.restapi.model.Organization;
import com.piotrgz.restapi.service.ReservationService;
import com.piotrgz.restapi.service.ConferenceRoomService;
import com.piotrgz.restapi.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/reservation")
public class ReservationController {


    private ReservationService reservationService;

    @Autowired
    public ReservationController(ReservationService reservationService, ConferenceRoomService conferenceRoomService, OrganizationService organizationService) {
        this.reservationService = Objects.requireNonNull(reservationService);


    }

    @PostMapping
    public ResponseEntity save(@Valid @RequestBody Reservation reservation) {
        return reservationService.save(reservation);
    }

    @GetMapping
    public ResponseEntity<List<Reservation>> getAll() {
        return ResponseEntity.ok(reservationService.getAll());
    }

    @GetMapping("/{name}")
    public ResponseEntity<Organization> findByName(@PathVariable("name") String name) {
        return reservationService.findByName(name);
    }


    @DeleteMapping("/{name}")
    public ResponseEntity delete(@PathVariable("name") String name) {
        return reservationService.delete(name);
    }


    @PutMapping("/{name}")
    public ResponseEntity update(@PathVariable String name, @Valid @RequestBody Reservation reservation) {
        return reservationService.update(name, reservation);
    }
}
