package com.piotrgz.restapi.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.piotrgz.restapi.exceptions.MyEntityAlreadyExistsException;
import com.piotrgz.restapi.exceptions.MyEntityNotFoundException;
import com.piotrgz.restapi.entity.Reservation;
import com.piotrgz.restapi.model.ReservationDTO;
import com.piotrgz.restapi.repository.ReservationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Service
public class ReservationService {

    private ReservationRepo reservationRepo;
    private OrganizationService organizationService;
    private ConferenceRoomService conferenceRoomService;
    private ObjectMapper objectMapper;



    @Autowired
    public ReservationService(ReservationRepo reservationRepo, OrganizationService organizationService, ConferenceRoomService conferenceRoomService, ObjectMapper objectMapper) {
        this.reservationRepo = Objects.requireNonNull(reservationRepo);
        this.organizationService = Objects.requireNonNull(organizationService);
        this.conferenceRoomService = Objects.requireNonNull(conferenceRoomService);
        this.objectMapper = Objects.requireNonNull(objectMapper);
    }

    public ReservationDTO save(ReservationDTO reservationDTO) throws IllegalArgumentException {

        if (reservationRepo.findById(reservationDTO.getName()).isPresent()) {
            throw new MyEntityAlreadyExistsException("Reservation with name " + reservationDTO.getName() + " already exists!");
        }

        reservationDTO.getOrganizationName().equals(organizationService.findByName(reservationDTO.getOrganizationName()));
        reservationDTO.getConferenceRoomName().equals(conferenceRoomService.findByName(reservationDTO.getConferenceRoomName()));

        return convertToDto(reservationRepo.save(convertToEntity(reservationDTO)));
    }

    public Iterable<ReservationDTO> getAll() {
        Stream<ReservationDTO> stream = StreamSupport.stream(reservationRepo.findAll().spliterator(), false)
                .map(reservation -> convertToDto(reservation));

        return stream::iterator;
    }


    public ReservationDTO findByName(String name) throws IllegalArgumentException {
        return convertToDto(reservationRepo.findById(name).orElseThrow(() -> new MyEntityNotFoundException("Reservation " + name + " has not been found")));
    }


    public ReservationDTO update(String name, ReservationDTO reservationDTO) throws IllegalArgumentException {

        if (reservationRepo.findById(reservationDTO.getName()).isPresent()) {
            throw new MyEntityAlreadyExistsException("Reservation with name " + name + " already exists!");
        }

        reservationDTO.getOrganizationName().equals(organizationService.findByName(reservationDTO.getOrganizationName()));
        reservationDTO.getConferenceRoomName().equals(conferenceRoomService.findByName(reservationDTO.getConferenceRoomName()));
        Reservation reservationToUpdate = convertToEntity(findByName(name));

        reservationToUpdate.setName(reservationDTO.getName());
        reservationToUpdate.setStartDate(reservationDTO.getStartDate());
        reservationToUpdate.setEndDate(reservationDTO.getEndDate());
        reservationToUpdate.setOrganizationName(reservationDTO.getOrganizationName());
        reservationToUpdate.setConferenceRoomName(reservationDTO.getConferenceRoomName());

        return convertToDto(reservationRepo.save(reservationToUpdate));
    }


    public void delete(String name) throws IllegalArgumentException {

        reservationRepo.delete(convertToEntity(findByName(name)));
    }

    private ReservationDTO convertToDto(Reservation reservation) {
        return objectMapper.convertValue(reservation, ReservationDTO.class);
    }

    private Reservation convertToEntity(ReservationDTO reservationDTO) {
        return objectMapper.convertValue(reservationDTO, Reservation.class);
    }
}
