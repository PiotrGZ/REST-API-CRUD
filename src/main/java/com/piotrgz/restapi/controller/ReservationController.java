package com.piotrgz.restapi.controller;


import com.piotrgz.restapi.model.ConferenceRoom;
import com.piotrgz.restapi.model.Reservation;
import com.piotrgz.restapi.model.Organization;
import com.piotrgz.restapi.modelDTO.OrganizationDTO;
import com.piotrgz.restapi.modelDTO.ReservationDTO;
import com.piotrgz.restapi.service.MyValidationException;
import com.piotrgz.restapi.service.ReservationService;
import com.piotrgz.restapi.service.ConferenceRoomService;
import com.piotrgz.restapi.service.OrganizationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/reservations")
public class ReservationController {


    private ReservationService reservationService;
    private ModelMapper modelMapper;

    @Autowired
    public ReservationController(ReservationService reservationService, ModelMapper modelMapper) {
        this.reservationService = Objects.requireNonNull(reservationService);
        this.modelMapper = Objects.requireNonNull(modelMapper);
    }

    @PostMapping
    public ResponseEntity save(@Valid @RequestBody ReservationDTO reservationDTO) {
        try {
            return ResponseEntity.ok(reservationService.save(convertToEntity(reservationDTO)));
        } catch (MyValidationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<Iterable<Reservation>> getAll() {
        return ResponseEntity.ok(reservationService.getAll());
    }

    @GetMapping("/{name}")
    public ResponseEntity findByName(@PathVariable("name") String name) {
        try {
            return ResponseEntity.ok(convertToDto(reservationService.findByName(name)));
        } catch (MyValidationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @DeleteMapping("/{name}")
    public ResponseEntity delete(@PathVariable("name") String name) {
        try {
            reservationService.delete(name);
            return ResponseEntity.ok().build();
        } catch (MyValidationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @PutMapping("/{name}")
    public ResponseEntity update(@PathVariable String name, @Valid @RequestBody ReservationDTO reservationDTO) {
        try {
            return ResponseEntity.ok(convertToDto(reservationService.update(name, convertToEntity(reservationDTO))));
        } catch (MyValidationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    private ReservationDTO convertToDto(Reservation reservation) {
        return modelMapper.map(reservation, ReservationDTO.class);
    }

    private Reservation convertToEntity(ReservationDTO reservationDTO) {
        return modelMapper.map(reservationDTO, Reservation.class);
    }
}
