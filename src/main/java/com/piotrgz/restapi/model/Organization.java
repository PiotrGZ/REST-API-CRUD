package com.piotrgz.restapi.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Organization {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;
    @OneToMany
    private List<ConferenceRoomReservation> conferenceRoomReservationCollection;

    public Organization() {
    }

    public List<ConferenceRoomReservation> getConferenceRoomReservationCollection() {
        return conferenceRoomReservationCollection;
    }

    public void setConferenceRoomReservationCollection(List<ConferenceRoomReservation> conferenceRoomReservationCollection) {
        this.conferenceRoomReservationCollection = conferenceRoomReservationCollection;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}