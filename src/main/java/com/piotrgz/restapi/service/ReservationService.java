package com.piotrgz.restapi.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.piotrgz.restapi.entity.Reservation;
import com.piotrgz.restapi.exceptions.EntityAlreadyExistsException;
import com.piotrgz.restapi.exceptions.EntityNotFoundException;
import com.piotrgz.restapi.model.ReservationDTO;
import com.piotrgz.restapi.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ReservationService {

    private ReservationRepository reservationRepository;
    private OrganizationService organizationService;
    private ConferenceRoomService conferenceRoomService;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository, OrganizationService organizationService, ConferenceRoomService conferenceRoomService, ObjectMapper objectMapper) {
        this.reservationRepository = Objects.requireNonNull(reservationRepository);
        this.organizationService = Objects.requireNonNull(organizationService);
        this.conferenceRoomService = Objects.requireNonNull(conferenceRoomService);
    }

    public ReservationDTO save(ReservationDTO reservationDTO) {
        if (reservationRepository.findById(reservationDTO.getName()).isPresent()) {
            throw new EntityAlreadyExistsException("Reservation with name " + reservationDTO.getName() + " already exists!");
        }
        reservationDTO.getOrganizationName().equals(organizationService.findByName(reservationDTO.getOrganizationName()));
        reservationDTO.getConferenceRoomName().equals(conferenceRoomService.findByName(reservationDTO.getConferenceRoomName()));
        return convertToDto(reservationRepository.save(convertToEntity(reservationDTO)));
    }

    public List<ReservationDTO> getAll() {
        return StreamSupport.stream(reservationRepository.findAll().spliterator(), false).map(reservation -> convertToDto(reservation)).collect(Collectors.toList());
    }

    public ReservationDTO findByName(String name) {
        return convertToDto(reservationRepository.findById(name).orElseThrow(() -> new EntityNotFoundException("Reservation " + name + " has not been found")));
    }

    public ReservationDTO update(String name, ReservationDTO reservationDTO) {
        if (reservationRepository.findById(reservationDTO.getName()).isPresent()) {
            throw new EntityAlreadyExistsException("Reservation with name " + name + " already exists!");
        }

        reservationDTO.getOrganizationName().equals(organizationService.findByName(reservationDTO.getOrganizationName()));
        reservationDTO.getConferenceRoomName().equals(conferenceRoomService.findByName(reservationDTO.getConferenceRoomName()));
        Reservation reservationToUpdate = convertToEntity(findByName(name));

        reservationToUpdate.setName(reservationDTO.getName());
        reservationToUpdate.setStartDate(reservationDTO.getStartDate());
        reservationToUpdate.setEndDate(reservationDTO.getEndDate());
        reservationToUpdate.setOrganizationName(reservationDTO.getOrganizationName());
        reservationToUpdate.setConferenceRoomName(reservationDTO.getConferenceRoomName());

        return convertToDto(reservationRepository.save(reservationToUpdate));
    }

    public void delete(String name) {

        reservationRepository.delete(convertToEntity(findByName(name)));
    }

    private ReservationDTO convertToDto(Reservation reservation) {
        ReservationDTO reservationDTO = new ReservationDTO();
        reservationDTO.setConferenceRoomName(reservation.getConferenceRoomName());
        reservationDTO.setOrganizationName(reservation.getOrganizationName());
        reservationDTO.setName(reservation.getName());
        reservationDTO.setEndDate(reservation.getEndDate());
        reservationDTO.setStartDate(reservation.getStartDate());
        return reservationDTO;
    }

    private Reservation convertToEntity(ReservationDTO reservationDTO) {
        Reservation reservation = new Reservation();
        reservation.setConferenceRoomName(reservationDTO.getConferenceRoomName());
        reservation.setOrganizationName(reservationDTO.getOrganizationName());
        reservation.setName(reservationDTO.getName());
        reservation.setEndDate(reservationDTO.getEndDate());
        reservation.setStartDate(reservationDTO.getStartDate());
        return reservation;
    }
}
