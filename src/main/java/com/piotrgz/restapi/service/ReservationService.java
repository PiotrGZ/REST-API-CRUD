package com.piotrgz.restapi.service;

import com.google.common.collect.Lists;
import com.piotrgz.restapi.entity.Reservation;
import com.piotrgz.restapi.exceptions.EntityAlreadyExistsException;
import com.piotrgz.restapi.exceptions.EntityNotFoundException;
import com.piotrgz.restapi.model.ReservationDTO;
import com.piotrgz.restapi.repository.ReservationRepository;
import com.piotrgz.restapi.validator.ReservatoinValidator;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final OrganizationService organizationService;
    private final ConferenceRoomService conferenceRoomService;
    private final ReservatoinValidator reservatoinValidator;

    private ReservationService(ReservationRepository reservationRepository, OrganizationService organizationService, ConferenceRoomService conferenceRoomService) {
        this.reservationRepository = Objects.requireNonNull(reservationRepository);
        this.organizationService = Objects.requireNonNull(organizationService);
        this.conferenceRoomService = Objects.requireNonNull(conferenceRoomService);
        this.reservatoinValidator=Objects.requireNonNull(new ReservatoinValidator(reservationRepository));
    }

    public ReservationDTO save(ReservationDTO reservationDTO) {
        if (reservationRepository.findById(reservationDTO.getName()).isPresent()) {
            throw new EntityAlreadyExistsException("Reservation with name " + reservationDTO.getName() + " already exists!");
        }

        organizationService.findByName(reservationDTO.getOrganizationName());
        conferenceRoomService.findByName(reservationDTO.getConferenceRoomName());

        reservatoinValidator.trimSecondsAndNanoseconds(reservationDTO);
        reservatoinValidator.areEndAndStartDatesPassingValidationOrThrowException(reservationDTO, reservationDTO.getStartDate(), reservationDTO.getEndDate());

        return convertToDto(reservationRepository.save(convertToEntity(reservationDTO)));
    }


    public List<ReservationDTO> getAll() {
        return Lists.newArrayList(reservationRepository.findAll()).stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public ReservationDTO findByName(String name) {
        return convertToDto(reservationRepository.findById(name).orElseThrow(() -> new EntityNotFoundException("Reservation " + name + " has not been found")));
    }

    public ReservationDTO update(String name, ReservationDTO reservationDTO) {
        if (reservationRepository.findById(reservationDTO.getName()).isPresent()) {
            throw new EntityAlreadyExistsException("Reservation with name " + name + " already exists!");
        }

        organizationService.findByName(reservationDTO.getOrganizationName());
        conferenceRoomService.findByName(reservationDTO.getConferenceRoomName());
        Reservation reservationToUpdate = convertToEntity(findByName(name));
        BeanUtils.copyProperties(reservationDTO, reservationToUpdate);

        return save(convertToDto(reservationToUpdate));
    }

    public void delete(String name) {

        reservationRepository.delete(convertToEntity(findByName(name)));
    }

    private ReservationDTO convertToDto(Reservation reservation) {
        ReservationDTO reservationDTO = new ReservationDTO();
        BeanUtils.copyProperties(reservation, reservationDTO);
        return reservationDTO;
    }

    private Reservation convertToEntity(ReservationDTO reservationDTO) {
        Reservation reservation = new Reservation();
        BeanUtils.copyProperties(reservationDTO, reservation);
        return reservation;
    }
}
