package com.piotrgz.restapi.controller;

import com.piotrgz.restapi.model.ConferenceRoomDTO;
import com.piotrgz.restapi.service.ConferenceRoomService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/conferencerooms")
class ConferenceRoomController {
    private final ConferenceRoomService conferenceRoomService;

    private ConferenceRoomController(ConferenceRoomService conferenceRoomService) {
        this.conferenceRoomService = Objects.requireNonNull(conferenceRoomService);
    }

    @PostMapping
    private ConferenceRoomDTO save(@Valid @RequestBody ConferenceRoomDTO conferenceRoomDTO) {
        return conferenceRoomService.save(conferenceRoomDTO);
    }

    @GetMapping
    private List<ConferenceRoomDTO> getAll() {
        return conferenceRoomService.getAll();
    }

    @GetMapping("/{name}")
    private ConferenceRoomDTO findByName(@PathVariable("name") String name) {
        return conferenceRoomService.findByName(name);
    }

    @DeleteMapping("/{name}")
    private void delete(@PathVariable("name") String name) {
        conferenceRoomService.delete(name);
    }

    @PutMapping("/{name}")
    private ConferenceRoomDTO update(@PathVariable String name, @Valid @RequestBody ConferenceRoomDTO conferenceRoomDTO) {
        return conferenceRoomService.update(name, conferenceRoomDTO);
    }
}