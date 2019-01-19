package com.piotrgz.restapi.controller;

import com.piotrgz.restapi.model.ConferenceRoom;


import com.piotrgz.restapi.modelDTO.ConferenceRoomDTO;
import com.piotrgz.restapi.service.ConferenceRoomService;
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
        this.modelMapper = Objects.requireNonNull(modelMapper);
    }

    @PostMapping
    public ConferenceRoomDTO save(@Valid @RequestBody ConferenceRoomDTO conferenceRoomDTO) throws IllegalArgumentException {
        return convertToDto(conferenceRoomService.save(convertToEntity(conferenceRoomDTO)));
    }

    @GetMapping
    public ResponseEntity<Iterable<ConferenceRoom>> getAll() {
        return ResponseEntity.ok(conferenceRoomService.getAll());
    }

    @GetMapping("/{name}")
    public ConferenceRoomDTO findByName(@PathVariable("name") String name) throws IllegalArgumentException {
        return convertToDto(conferenceRoomService.findByName(name));
    }


    @DeleteMapping("/{name}")
    public void delete(@PathVariable("name") String name) throws IllegalArgumentException {
        conferenceRoomService.delete(name);
    }


    @PutMapping("/{name}")
    public ConferenceRoomDTO update(@PathVariable String name, @Valid @RequestBody ConferenceRoomDTO conferenceRoomDTO) throws IllegalArgumentException {
        return convertToDto(conferenceRoomService.update(name, convertToEntity(conferenceRoomDTO)));
    }

    private ConferenceRoomDTO convertToDto(ConferenceRoom conferenceRoom) {
        return modelMapper.map(conferenceRoom, ConferenceRoomDTO.class);
    }

    private ConferenceRoom convertToEntity(ConferenceRoomDTO conferenceRoomDTO) {
        return modelMapper.map(conferenceRoomDTO, ConferenceRoom.class);
    }
}