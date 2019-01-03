package com.piotrgz.restapi.model;



import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
public class Organization {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @NotNull(message = "Please provide name")
    @Size(min = 2, max = 20, message = "Please provide name in range of 2-20 characters")
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