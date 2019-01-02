package com.piotrgz.restapi.models;

import javax.persistence.*;
import java.util.List;

@Entity
public class ConferenceRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int Id;
    private String name;
    private String OptionalId;
    private int floor;
    private boolean isAvailable;
    private int numberOfSeats;
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

    public String getOptionalId() {
        return OptionalId;
    }

    public void setOptionalId(String optionalId) {
        OptionalId = optionalId;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }
}