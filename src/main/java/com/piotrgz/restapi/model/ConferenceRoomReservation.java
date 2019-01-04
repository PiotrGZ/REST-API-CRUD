package com.piotrgz.restapi.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class ConferenceRoomReservation {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @NotNull
    @DateTimeFormat
    private String startDate;
    @NotNull
    @DateTimeFormat
    private String endDate;
    @ManyToOne(fetch = FetchType.EAGER)
//    @JsonIgnore
    @JoinColumn(name = "organization_id")
    private Organization organization;
    @ManyToOne(fetch = FetchType.EAGER)
//    @JsonIgnore
    @JoinColumn(name = "conferenceRoom_id")
    private ConferenceRoom conferenceRoom;

    public ConferenceRoomReservation() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public ConferenceRoom getConferenceRoom() {
        return conferenceRoom;
    }

    public void setConferenceRoom(ConferenceRoom conferenceRoom) {
        this.conferenceRoom = conferenceRoom;
    }
}
