package com.piotrgz.restapi.service;

import com.google.common.collect.Lists;
import com.piotrgz.restapi.entity.Reservation;
import com.piotrgz.restapi.exceptions.EntityAlreadyExistsException;
import com.piotrgz.restapi.exceptions.EntityNotFoundException;
import com.piotrgz.restapi.exceptions.TimeSetupException;
import com.piotrgz.restapi.model.ReservationDTO;
import com.piotrgz.restapi.repository.ReservationRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReservationService {

    public static final int MIN_RESERVATION_TIME_IN_MINUTES = 5;
    public static final int MAX_RESERVATION_TIME_IN_HRS = 2;
    private final ReservationRepository reservationRepository;
    private final OrganizationService organizationService;
    private final ConferenceRoomService conferenceRoomService;

    private ReservationService(ReservationRepository reservationRepository, OrganizationService organizationService, ConferenceRoomService conferenceRoomService) {
        this.reservationRepository = Objects.requireNonNull(reservationRepository);
        this.organizationService = Objects.requireNonNull(organizationService);
        this.conferenceRoomService = Objects.requireNonNull(conferenceRoomService);
    }

    public ReservationDTO save(ReservationDTO reservationDTO) {
        if (reservationRepository.findById(reservationDTO.getName()).isPresent()) {
            throw new EntityAlreadyExistsException("Reservation with name " + reservationDTO.getName() + " already exists!");
        }

        organizationService.findByName(reservationDTO.getOrganizationName());
        conferenceRoomService.findByName(reservationDTO.getConferenceRoomName());
        trimSecondsAndNanoseconds(reservationDTO);
        areEndAndStartDatesPassingValidationOrThrowException(reservationDTO);

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

        return convertToDto(reservationRepository.save(reservationToUpdate));
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

    private void trimSecondsAndNanoseconds(ReservationDTO reservationDTO) {
        LocalDateTime startDateTrimmed = reservationDTO.getStartDate().minusNanos(reservationDTO.getStartDate().getNano()).minusSeconds(reservationDTO.getStartDate().getSecond());
        LocalDateTime endDateTrimmed = reservationDTO.getEndDate().minusNanos(reservationDTO.getEndDate().getNano()).minusSeconds(reservationDTO.getEndDate().getSecond());

        reservationDTO.setStartDate(startDateTrimmed);
        reservationDTO.setEndDate(endDateTrimmed);
    }

    private void areEndAndStartDatesPassingValidationOrThrowException(ReservationDTO reservation) {
        LocalDateTime startDate = reservation.getStartDate();
        LocalDateTime endDate = reservation.getEndDate();

        Optional.of(startDate).filter(t -> t.isBefore(endDate)).orElseThrow(() -> new TimeSetupException("Start date must be before end date"));
        Optional.of(startDate).filter(t -> t.plusMinutes(MIN_RESERVATION_TIME_IN_MINUTES).isBefore(endDate) || t.plusMinutes(MIN_RESERVATION_TIME_IN_MINUTES).isEqual(endDate)).orElseThrow(() -> new TimeSetupException("Minimum reservation time is 5 minutes"));
        Optional.of(startDate).filter(t -> t.plusHours(MAX_RESERVATION_TIME_IN_HRS).isAfter(endDate) || t.plusHours(MAX_RESERVATION_TIME_IN_HRS).isEqual(endDate)).orElseThrow(() -> new TimeSetupException("Maximum reservation time is 2 hours"));

        List<ReservationDTO> reservationList = new ArrayList<>();

        getAll().stream().filter(t -> t.getConferenceRoomName().equals(reservation.getConferenceRoomName())).filter(t -> startDate.isAfter(t.getStartDate()) && startDate.isBefore(t.getEndDate())).forEach(t -> reservationList.add(t));
        getAll().stream().filter(t -> t.getConferenceRoomName().equals(reservation.getConferenceRoomName())).filter(t -> endDate.isAfter(t.getStartDate()) && endDate.isBefore(t.getEndDate())).forEach(t -> reservationList.add(t));
        getAll().stream().filter(t -> t.getConferenceRoomName().equals(reservation.getConferenceRoomName())).filter(t -> startDate.isEqual(t.getStartDate())).forEach(t -> reservationList.add(t));
        getAll().stream().filter(t -> t.getConferenceRoomName().equals(reservation.getConferenceRoomName())).filter(t -> endDate.isEqual(t.getEndDate())).forEach(t -> reservationList.add(t));

        if (!reservationList.isEmpty()) {
            throw new TimeSetupException("Conference room is already booked at this time");
        }
    }
}
