package com.piotrgz.restapi.service;

import com.google.common.collect.Lists;
import com.piotrgz.restapi.entity.ConferenceRoom;
import com.piotrgz.restapi.exceptions.EntityAlreadyExistsException;
import com.piotrgz.restapi.exceptions.EntityNotFoundException;
import com.piotrgz.restapi.model.ConferenceRoomDTO;
import com.piotrgz.restapi.repository.ConferenceRoomRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Service
public class ConferenceRoomService {

    private ConferenceRoomRepository conferenceRoomRepository;

    private ConferenceRoomService(ConferenceRoomRepository conferenceRoomRepository) {
        this.conferenceRoomRepository = Objects.requireNonNull(conferenceRoomRepository);
    }

    public ConferenceRoomDTO save(ConferenceRoomDTO conferenceRoomDTO) {
        if (conferenceRoomRepository.findById(conferenceRoomDTO.getName()).isPresent()) {
            throw new EntityAlreadyExistsException("Conference room with name " + conferenceRoomDTO.getName() + " already exists!");
        }
        return convertToDto(conferenceRoomRepository.save(convertToEntity(conferenceRoomDTO)));
    }

    public List<ConferenceRoomDTO> getAll() {
        return Lists.newArrayList(conferenceRoomRepository.findAll()).stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public ConferenceRoomDTO findByName(String name) {
        return convertToDto(conferenceRoomRepository.findById(name).orElseThrow(() -> new EntityNotFoundException("Conference room " + name + " has not been found")));
    }

    public ConferenceRoomDTO update(String name, ConferenceRoomDTO conferenceRoomDTO) {
        if (conferenceRoomRepository.findById(conferenceRoomDTO.getName()).isPresent()) {
            throw new EntityAlreadyExistsException("Conference room " + name + " already exists!");
        }
        ConferenceRoom conferenceRoomToUpdate = convertToEntity(findByName(name));
        BeanUtils.copyProperties(conferenceRoomDTO, conferenceRoomToUpdate);

        return convertToDto(conferenceRoomRepository.save(conferenceRoomToUpdate));
    }

    public void delete(String name) {
        conferenceRoomRepository.delete(convertToEntity(findByName(name)));
    }

    private ConferenceRoomDTO convertToDto(ConferenceRoom conferenceRoom) {
        ConferenceRoomDTO conferenceRoomDTO = new ConferenceRoomDTO();
        BeanUtils.copyProperties(conferenceRoom, conferenceRoomDTO);
        return conferenceRoomDTO;
    }

    private ConferenceRoom convertToEntity(ConferenceRoomDTO conferenceRoomDTO) {
        ConferenceRoom conferenceRoom = new ConferenceRoom();
        BeanUtils.copyProperties(conferenceRoomDTO, conferenceRoom);
        return conferenceRoom;
    }
}
