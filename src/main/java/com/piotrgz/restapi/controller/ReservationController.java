package com.piotrgz.restapi.controller;

import com.piotrgz.restapi.model.ReservationDTO;
import com.piotrgz.restapi.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/reservations")
public class ReservationController {
    private ReservationService reservationService;

    @Autowired

    public ReservationController(ReservationService reservationService) {
        this.reservationService = Objects.requireNonNull(reservationService);
    }

    @PostMapping
    public ReservationDTO save(@Valid @RequestBody ReservationDTO reservationDTO) {
        return reservationService.save(reservationDTO);
    }

    @GetMapping
    public List<ReservationDTO> getAll() {
        return reservationService.getAll();
    }

    @GetMapping("/{name}")
    public ReservationDTO findByName(@PathVariable("name") String name) {
        return reservationService.findByName(name);
    }

    @DeleteMapping("/{name}")
    public void delete(@PathVariable("name") String name) {
        reservationService.delete(name);
    }

    @PutMapping("/{name}")
    public ReservationDTO update(@PathVariable String name, @Valid @RequestBody ReservationDTO reservationDTO) {
        return reservationService.update(name, reservationDTO);
    }
}
