package com.piotrgz.restapi.controller;

import com.piotrgz.restapi.model.ReservationDTO;
import com.piotrgz.restapi.service.ReservationService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/reservations")
class ReservationController {
    private final ReservationService reservationService;

    private ReservationController(ReservationService reservationService) {
        this.reservationService = Objects.requireNonNull(reservationService);
    }

    @PostMapping
    private ReservationDTO save(@Valid @RequestBody ReservationDTO reservationDTO) {
        return reservationService.save(reservationDTO);
    }

    @GetMapping
    private List<ReservationDTO> getAll() {
        return reservationService.getAll();
    }

    @GetMapping("/{name}")
    private ReservationDTO findByName(@PathVariable("name") String name) {
        return reservationService.findByName(name);
    }

    @DeleteMapping("/{name}")
    private void delete(@PathVariable("name") String name) {
        reservationService.delete(name);
    }

    @PutMapping("/{name}")
    private ReservationDTO update(@PathVariable String name, @Valid @RequestBody ReservationDTO reservationDTO) {
        return reservationService.update(name, reservationDTO);
    }
}
