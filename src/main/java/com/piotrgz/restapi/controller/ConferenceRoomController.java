package com.piotrgz.restapi.controller;

import com.piotrgz.restapi.model.ConferenceRoom;

import com.piotrgz.restapi.service.ConferenceRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;


@RestController
@RequestMapping("/conferenceroom")
public class ConferenceRoomController {

    private ConferenceRoomService conferenceRoomService;

    @Autowired
    public ConferenceRoomController(ConferenceRoomService conferenceRoomService) {
        this.conferenceRoomService = Objects.requireNonNull(conferenceRoomService);
    }

    @PostMapping
    public ResponseEntity save(@Valid @RequestBody ConferenceRoom conferenceRoom) {
        return conferenceRoomService.save(conferenceRoom);
    }

    @GetMapping
    public ResponseEntity<List<ConferenceRoom>> getAll() {
        return ResponseEntity.ok(conferenceRoomService.getAll());
    }

    @GetMapping("/{name}")
    public ResponseEntity<ConferenceRoom> findByName(@PathVariable("name") String name) {
        return conferenceRoomService.findByName(name);
    }


    @DeleteMapping("/{name}")
    public ResponseEntity delete(@PathVariable("name") String name) {
        return conferenceRoomService.delete(name);
    }


    @PutMapping("/{name}")
    public ResponseEntity update(@PathVariable String name, @Valid @RequestBody ConferenceRoom conferenceRoom) {
        return conferenceRoomService.update(name, conferenceRoom);
    }
}