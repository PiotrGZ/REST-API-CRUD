package com.piotrgz.restapi.validator;

import com.piotrgz.restapi.entity.Reservation;
import com.piotrgz.restapi.exceptions.TimeSetupException;
import com.piotrgz.restapi.model.ReservationDTO;
import com.piotrgz.restapi.repository.ReservationRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    public void isRoomFree(ReservationDTO reservationDTO, LocalDateTime startDate, LocalDateTime endDate) {

        List<Reservation> reservationsWithCollidingDuration = new ArrayList<>();

        reservationRepository.findAllByConferenceRoomName(reservationDTO.getConferenceRoomName())
                .stream()
                .filter(t ->
                        (startDate.isAfter(t.getStartDate()) && startDate.isBefore(t.getEndDate())) ||
                        (endDate.isAfter(t.getStartDate()) && endDate.isBefore(t.getEndDate())) ||
                        startDate.isEqual(t.getStartDate()) ||
                        endDate.isEqual(t.getEndDate()))
                .forEach(t -> reservationsWithCollidingDuration.add(t));

        if (!reservationsWithCollidingDuration.isEmpty()) {
            throw new TimeSetupException("Conference room is already booked at this time");
        }
    }

    public void isDurationValid(LocalDateTime startDate, LocalDateTime endDate) {
        if (startDate.isAfter(endDate)) {
            throw new TimeSetupException("Start date must be before end date");
        }
        if (startDate.plusMinutes(MIN_RESERVATION_TIME_IN_MINUTES).isAfter(endDate)) {
            throw new TimeSetupException("Minimum reservation time is 5 minutes");
        }
        if (startDate.plusHours(MAX_RESERVATION_TIME_IN_HRS).isBefore(endDate)) {
            throw new TimeSetupException("Maximum reservation time is 2 hours");
        }
    }
}
