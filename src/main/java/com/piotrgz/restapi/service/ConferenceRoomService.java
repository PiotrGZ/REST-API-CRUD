package com.piotrgz.restapi.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.piotrgz.restapi.exceptions.EntityAlreadyExistsException;
import com.piotrgz.restapi.exceptions.EntityNotFoundException;
import com.piotrgz.restapi.entity.ConferenceRoom;
import com.piotrgz.restapi.model.ConferenceRoomDTO;
import com.piotrgz.restapi.repository.ConferenceRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ConferenceRoomService {

    private ConferenceRoomRepository conferenceRoomRepository;
    private ObjectMapper objectMapper;

    @Autowired
    public ConferenceRoomService(ConferenceRoomRepository conferenceRoomRepository, ObjectMapper objectMapper) {
        this.conferenceRoomRepository = Objects.requireNonNull(conferenceRoomRepository);
        this.objectMapper = Objects.requireNonNull(objectMapper);
    }

    public ConferenceRoomDTO save(ConferenceRoomDTO conferenceRoomDTO) {
        if (conferenceRoomRepository.findById(conferenceRoomDTO.getName()).isPresent()) {
            throw new EntityAlreadyExistsException("Conference room with name " + conferenceRoomDTO.getName() + " already exists!");
        }
        return convertToDto(conferenceRoomRepository.save(convertToEntity(conferenceRoomDTO)));
    }

    public List<ConferenceRoomDTO> getAll() {
       return StreamSupport.stream(conferenceRoomRepository.findAll().spliterator(), false).map(organization -> convertToDto(organization)).collect(Collectors.toList());
    }

    public ConferenceRoomDTO findByName(String name) {
        return convertToDto(conferenceRoomRepository.findById(name).orElseThrow(() -> new EntityNotFoundException("Conference room " + name + " has not been found")));
    }

    public ConferenceRoomDTO update(String name, ConferenceRoomDTO conferenceRoomDTO) {
        if (conferenceRoomRepository.findById(conferenceRoomDTO.getName()).isPresent()) {
            throw new EntityAlreadyExistsException("Conference room " + name + " already exists!");
        }
        ConferenceRoom conferenceRoomToUpdate = convertToEntity(findByName(name));

        conferenceRoomToUpdate.setName(conferenceRoomDTO.getName());
        conferenceRoomToUpdate.setNumberOfSeats(conferenceRoomDTO.getNumberOfSeats());
        conferenceRoomToUpdate.setAvailable(conferenceRoomDTO.getAvailable());
        conferenceRoomToUpdate.setFloor(conferenceRoomDTO.getFloor());
        conferenceRoomToUpdate.setCommunicationInterface(conferenceRoomDTO.getCommunicationInterface());
        conferenceRoomToUpdate.setExternalPhoneNumber(conferenceRoomDTO.getExternalPhoneNumber());
        conferenceRoomToUpdate.setInternalPhoneNumber(conferenceRoomDTO.getInternalPhoneNumber());
        conferenceRoomToUpdate.setProjector(conferenceRoomDTO.getProjector());
        conferenceRoomToUpdate.setNumberOfLyingPlace(conferenceRoomDTO.getNumberOfLyingPlace());
        conferenceRoomToUpdate.setNumberOfStandingPlace(conferenceRoomDTO.getNumberOfStandingPlace());
        conferenceRoomToUpdate.setPhonePresent(conferenceRoomDTO.getPhonePresent());

        return convertToDto(conferenceRoomRepository.save(conferenceRoomToUpdate));
    }

    public void delete(String name) {
        conferenceRoomRepository.delete(convertToEntity(findByName(name)));
    }

    private ConferenceRoomDTO convertToDto(ConferenceRoom conferenceRoom) {
        return objectMapper.convertValue(conferenceRoom, ConferenceRoomDTO.class);
    }

    private ConferenceRoom convertToEntity(ConferenceRoomDTO conferenceRoomDTO) {
        return objectMapper.convertValue(conferenceRoomDTO, ConferenceRoom.class);
    }
}
