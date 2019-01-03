package com.piotrgz.restapi.controller;

import com.piotrgz.restapi.model.ConferenceRoom;

import com.piotrgz.restapi.service.ConferenceRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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


        String conferenceRoomName = conferenceRoom.getName();

        if (isNameValid(conferenceRoomName)) {
            conferenceRoomService.save(conferenceRoom);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Name is not valid");
        }
    }

    @GetMapping

    public ResponseEntity<List<ConferenceRoom>> getAll() {
        return ResponseEntity.ok(conferenceRoomService.getAll());
    }

    @DeleteMapping
    public ResponseEntity delete(@RequestParam int id) {
        if (isConferenceRoomPresent(id)) {
            conferenceRoomService.delete(id);
            return ResponseEntity.ok().build();
        } else
            return ResponseEntity.badRequest().body("Conference room with ID: " + id + " has not been found");
    }


    @PatchMapping
    public ResponseEntity update(@RequestParam int id, @Valid @RequestBody ConferenceRoom conferenceRoom) {


        String organizationName = conferenceRoom.getName();

        if (isConferenceRoomPresent(id) && isNameValid(organizationName)) {
            conferenceRoomService.update(id, conferenceRoom);
            return ResponseEntity.ok().build();
        } else
            return ResponseEntity.badRequest().body("Conference room with ID: " + id + " doesn't exist or name is not valid");
    }


    private boolean isConferenceRoomPresent(int id) {
        return conferenceRoomService.getAll().stream().anyMatch(t -> ((Integer) t.getId()).equals(id));
    }


    private boolean isNameValid(String name) {

        return !name.trim().isEmpty() &&
                !conferenceRoomService.getAll().stream().anyMatch(t -> t.getName().equals(name));
    }
}