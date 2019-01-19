package com.piotrgz.restapi.controller;


import com.piotrgz.restapi.entity.Reservation;
import com.piotrgz.restapi.model.ReservationDTO;
import com.piotrgz.restapi.service.ReservationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    public ReservationDTO save(@Valid @RequestBody ReservationDTO reservationDTO) throws IllegalArgumentException {
        return convertToDto(reservationService.save(convertToEntity(reservationDTO)));
    }

    @GetMapping
    public ResponseEntity<Iterable<Reservation>> getAll() {
        return ResponseEntity.ok(reservationService.getAll());
    }

    @GetMapping("/{name}")
    public ReservationDTO findByName(@PathVariable("name") String name) throws IllegalArgumentException {
           return convertToDto(reservationService.findByName(name));
    }


    @DeleteMapping("/{name}")
    public void delete(@PathVariable("name") String name) throws IllegalArgumentException {
            reservationService.delete(name);
    }


    @PutMapping("/{name}")
    public ReservationDTO update(@PathVariable String name, @Valid @RequestBody ReservationDTO reservationDTO) throws IllegalArgumentException{
            return convertToDto(reservationService.update(name, convertToEntity(reservationDTO)));
    }

    private ReservationDTO convertToDto(Reservation reservation) {
        return modelMapper.map(reservation, ReservationDTO.class);
    }

    private Reservation convertToEntity(ReservationDTO reservationDTO) {
        return modelMapper.map(reservationDTO, Reservation.class);
    }
}
