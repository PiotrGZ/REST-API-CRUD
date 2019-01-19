package com.piotrgz.restapi.controller;

import com.piotrgz.restapi.model.ConferenceRoom;


import com.piotrgz.restapi.modelDTO.ConferenceRoomDTO;
import com.piotrgz.restapi.service.ConferenceRoomService;
import com.piotrgz.restapi.service.MyValidationException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Objects;


@RestController
@RequestMapping("/conferencerooms")
public class ConferenceRoomController {

    private ModelMapper modelMapper;
    private ConferenceRoomService conferenceRoomService;

    @Autowired
    public ConferenceRoomController(ConferenceRoomService conferenceRoomService, ModelMapper modelMapper) {
        this.conferenceRoomService = Objects.requireNonNull(conferenceRoomService);
        this.modelMapper=Objects.requireNonNull(modelMapper);
    }

    @PostMapping
    public ResponseEntity save(@Valid @RequestBody ConferenceRoomDTO conferenceRoomDTO) {
        try {
            return ResponseEntity.ok(conferenceRoomService.save(convertToEntity(conferenceRoomDTO)));
        } catch (MyValidationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<Iterable<ConferenceRoom>> getAll() {
        return ResponseEntity.ok(conferenceRoomService.getAll());
    }

    @GetMapping("/{name}")
    public ResponseEntity findByName(@PathVariable("name") String name) {
        try {
            return ResponseEntity.ok(convertToDto(conferenceRoomService.findByName(name)));
        } catch (MyValidationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @DeleteMapping("/{name}")
    public ResponseEntity delete(@PathVariable("name") String name) {
        try {
            conferenceRoomService.delete(name);
            return ResponseEntity.ok().build();
        } catch (MyValidationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @PutMapping("/{name}")
    public ResponseEntity update(@PathVariable String name, @Valid @RequestBody ConferenceRoomDTO conferenceRoomDTO) {
        try {
            return ResponseEntity.ok(convertToDto(conferenceRoomService.update(name, convertToEntity(conferenceRoomDTO))));
        } catch (MyValidationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    private ConferenceRoomDTO convertToDto(ConferenceRoom conferenceRoom) {
        return modelMapper.map(conferenceRoom, ConferenceRoomDTO.class);
    }

    private ConferenceRoom convertToEntity(ConferenceRoomDTO conferenceRoomDTO){
        return modelMapper.map(conferenceRoomDTO, ConferenceRoom.class);
    }
}