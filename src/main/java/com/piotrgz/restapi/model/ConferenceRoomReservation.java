package com.piotrgz.restapi.model;


import javax.persistence.*;

@Entity
public class ConferenceRoomReservation {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int Id;
    private String startDate;
    private String endDate;
    @ManyToOne
    private Organization organization;
    @ManyToOne
    private ConferenceRoom conferenceRoom;


    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
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
