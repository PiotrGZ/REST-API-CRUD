package com.piotrgz.restapi.validator;

import com.piotrgz.restapi.entity.Reservation;
import com.piotrgz.restapi.exceptions.TimeSetupException;
import com.piotrgz.restapi.model.ReservationDTO;
import com.piotrgz.restapi.repository.ReservationRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ReservatoinValidator {

    public static final int MIN_RESERVATION_TIME_IN_MINUTES = 5;
    public static final int MAX_RESERVATION_TIME_IN_HRS = 2;
    private final ReservationRepository reservationRepository;

    public ReservatoinValidator(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public void trimSecondsAndNanoseconds(ReservationDTO reservationDTO) {
        LocalDateTime startDateTrimmed = reservationDTO.getStartDate().minusNanos(reservationDTO.getStartDate().getNano()).minusSeconds(reservationDTO.getStartDate().getSecond());
        LocalDateTime endDateTrimmed = reservationDTO.getEndDate().minusNanos(reservationDTO.getEndDate().getNano()).minusSeconds(reservationDTO.getEndDate().getSecond());

        reservationDTO.setStartDate(startDateTrimmed);
        reservationDTO.setEndDate(endDateTrimmed);
    }

    public void areEndAndStartDatesPassingValidationOrThrowException(ReservationDTO reservationDTO, LocalDateTime startDate, LocalDateTime endDate) {

        Optional.of(startDate).filter(t -> t.isBefore(endDate)).orElseThrow(() -> new TimeSetupException("Start date must be before end date"));
        Optional.of(startDate).filter(t -> t.plusMinutes(MIN_RESERVATION_TIME_IN_MINUTES).isBefore(endDate) || t.plusMinutes(MIN_RESERVATION_TIME_IN_MINUTES).isEqual(endDate)).orElseThrow(() -> new TimeSetupException("Minimum reservation time is 5 minutes"));
        Optional.of(startDate).filter(t -> t.plusHours(MAX_RESERVATION_TIME_IN_HRS).isAfter(endDate) || t.plusHours(MAX_RESERVATION_TIME_IN_HRS).isEqual(endDate)).orElseThrow(() -> new TimeSetupException("Maximum reservation time is 2 hours"));

        List<Reservation> reservationList = new ArrayList<>();
        List<Reservation> allByConferenceRoomName = reservationRepository.findAllByConferenceRoomName(reservationDTO.getConferenceRoomName());

        allByConferenceRoomName.stream().filter(t -> startDate.isAfter(t.getStartDate()) && startDate.isBefore(t.getEndDate())).forEach(t -> reservationList.add(t));
        allByConferenceRoomName.stream().filter(t -> endDate.isAfter(t.getStartDate()) && endDate.isBefore(t.getEndDate())).forEach(t -> reservationList.add(t));
        allByConferenceRoomName.stream().filter(t -> startDate.isEqual(t.getStartDate())).forEach(t -> reservationList.add(t));
        allByConferenceRoomName.stream().filter(t -> endDate.isEqual(t.getEndDate())).forEach(t -> reservationList.add(t));

        if (!reservationList.isEmpty()) {
            throw new TimeSetupException("Conference room is already booked at this time");
        }
    }
}
