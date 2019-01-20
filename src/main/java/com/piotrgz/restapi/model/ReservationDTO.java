package com.piotrgz.restapi.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

public class ReservationDTO {

    @NotBlank(message = "Valid name shouldn't be empty and consist only white characters")
    @Size(min = 2, max = 20, message = "Name should be between 2 and 20 characters")
    private String name;
    @NotBlank(message = "Please provide organization name, valid name shouldn't be empty and consist only white characters")
    @Size(min = 2, max = 20, message = "Please provide organization name in range of 2-20 characters")
    private String organizationName;
    @NotBlank(message = "Please provide conference room name, valid name shouldn't be empty and consist only white characters")
    @Size(min = 2, max = 20, message = "Please provide conference room name in range of 2-20 characters")
    private String conferenceRoomName;
    @NotNull(message = "Please enter start date")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime startDate;
    @NotNull(message = "Please enter end date")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime endDate;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getConferenceRoomName() {
        return conferenceRoomName;
    }

    public void setConferenceRoomName(String conferenceRoomName) {
        this.conferenceRoomName = conferenceRoomName;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }
}
