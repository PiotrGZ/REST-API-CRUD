package com.piotrgz.restapi.models;

import javax.persistence.*;
import java.util.List;

@Entity
public class Organization {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int Id;
    private String name;
    @OneToMany
    private List<ConferenceRoomReservation> conferenceRoomReservationCollection;

    public List<ConferenceRoomReservation> getConferenceRoomReservationCollection() {
        return conferenceRoomReservationCollection;
    }

    public void setConferenceRoomReservationCollection(List<ConferenceRoomReservation> conferenceRoomReservationCollection) {
        this.conferenceRoomReservationCollection = conferenceRoomReservationCollection;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}