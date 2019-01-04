package com.piotrgz.restapi.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class ConferenceRoomReservation {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @NotNull(message = "Please provide organization name")
    @Size(min = 2, max = 20, message = "Please provide organization name in range of 2-20 characters")
    private String organizationName;
    @NotNull(message = "Please provide conference room name")
    @Size(min = 2, max = 20, message = "Please provide conference room name in range of 2-20 characters")
    private String conferenceRoomName;
    @NotNull
    @DateTimeFormat
    private String startDate;
    @NotNull
    @DateTimeFormat
    private String endDate;
    @ManyToOne
//            (cascade = CascadeType.MERGE)
    @JsonIgnore
    private Organization organization;
    @ManyToOne
//            (cascade = CascadeType.MERGE)
    @JsonIgnore
    private ConferenceRoom conferenceRoom;

    public ConferenceRoomReservation() {
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
