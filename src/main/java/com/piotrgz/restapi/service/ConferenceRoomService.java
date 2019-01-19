package com.piotrgz.restapi.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.piotrgz.restapi.exceptions.MyEntityAlreadyExistsException;
import com.piotrgz.restapi.exceptions.MyEntityNotFoundException;
import com.piotrgz.restapi.entity.ConferenceRoom;
import com.piotrgz.restapi.model.ConferenceRoomDTO;
import com.piotrgz.restapi.repository.ConferenceRoomRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.Objects;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;


@Service
public class ConferenceRoomService {

    private ConferenceRoomRepo conferenceRoomRepo;
    private ObjectMapper objectMapper;

    @Autowired
    public ConferenceRoomService(ConferenceRoomRepo conferenceRoomRepo, ObjectMapper objectMapper) {
        this.conferenceRoomRepo = Objects.requireNonNull(conferenceRoomRepo);
        this.objectMapper = Objects.requireNonNull(objectMapper);
    }

    public ConferenceRoomDTO save(ConferenceRoomDTO conferenceRoomDTO) throws IllegalArgumentException {

        if (conferenceRoomRepo.findById(conferenceRoomDTO.getName()).isPresent()) {
            throw new MyEntityAlreadyExistsException("Conference room with name " + conferenceRoomDTO.getName() + " already exists!");
        }
        return convertToDto(conferenceRoomRepo.save(convertToEntity(conferenceRoomDTO)));
    }

    public Iterable<ConferenceRoomDTO> getAll() {

        Stream<ConferenceRoomDTO> stream = StreamSupport.stream(conferenceRoomRepo.findAll().spliterator(), false)
                .map(organization -> convertToDto(organization));

        return stream::iterator;
    }


    public ConferenceRoomDTO findByName(String name) throws IllegalArgumentException {
        return convertToDto(conferenceRoomRepo.findById(name).orElseThrow(() -> new MyEntityNotFoundException("Conference room " + name + " has not been found")));
    }


    public ConferenceRoomDTO update(String name, ConferenceRoomDTO conferenceRoomDTO) throws IllegalArgumentException {

        if (conferenceRoomRepo.findById(conferenceRoomDTO.getName()).isPresent()) {
            throw new MyEntityAlreadyExistsException("Conference room " + name + " already exists!");
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

        return convertToDto(conferenceRoomRepo.save(conferenceRoomToUpdate));
    }


    public void delete(String name) throws IllegalArgumentException {

        conferenceRoomRepo.delete(convertToEntity(findByName(name)));
    }

    private ConferenceRoomDTO convertToDto(ConferenceRoom conferenceRoom) {
        return objectMapper.convertValue(conferenceRoom, ConferenceRoomDTO.class);
    }

    private ConferenceRoom convertToEntity(ConferenceRoomDTO conferenceRoomDTO) {
        return objectMapper.convertValue(conferenceRoomDTO, ConferenceRoom.class);
    }
}
