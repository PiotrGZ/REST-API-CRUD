package com.piotrgz.restapi.controller;

import com.piotrgz.restapi.model.ConferenceRoomDTO;
import com.piotrgz.restapi.service.ConferenceRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Objects;

@RestController
@RequestMapping("/conferencerooms")
public class ConferenceRoomController {
    private ConferenceRoomService conferenceRoomService;

    @Autowired
    public ConferenceRoomController(ConferenceRoomService conferenceRoomService) {
        this.conferenceRoomService = Objects.requireNonNull(conferenceRoomService);
    }

    @PostMapping
    public ConferenceRoomDTO save(@Valid @RequestBody ConferenceRoomDTO conferenceRoomDTO) {
        return conferenceRoomService.save(conferenceRoomDTO);
    }

    @GetMapping
    public Iterable<ConferenceRoomDTO> getAll() {
        return conferenceRoomService.getAll();
    }

    @GetMapping("/{name}")
    public ConferenceRoomDTO findByName(@PathVariable("name") String name) {
        return conferenceRoomService.findByName(name);
    }

    @DeleteMapping("/{name}")
    public void delete(@PathVariable("name") String name) {
        conferenceRoomService.delete(name);
    }

    @PutMapping("/{name}")
    public ConferenceRoomDTO update(@PathVariable String name, @Valid @RequestBody ConferenceRoomDTO conferenceRoomDTO) {
        return conferenceRoomService.update(name, conferenceRoomDTO);
    }
}