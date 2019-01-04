package com.piotrgz.restapi.model;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.List;

@Entity
public class ConferenceRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotNull
    private int id;
    @NotNull(message = "Please provide name")
    @Size(min = 2, max = 20, message = "Name should be between 2 and 20 characters")
    @Column(unique = true)
    private String name;
    @NotNull(message = "Please provide floor number from 0 to 10")
    @Max(value = 10, message = "Floor number must be less or equal 10")
    @Min(value = 0, message = "Floor number must be greater or equal 0")
    private Integer floor;
    @NotNull(message = "Please provide valid availability")
    private Boolean isAvailable;
    @NotNull(message = "Please provide number of seats")
    private Integer numberOfSeats;
    @OneToMany
            (mappedBy = "conferenceRoom", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<ConferenceRoomReservation> conferenceRoomReservationCollection;


    public ConferenceRoom() {
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

    public Integer getFloor() {
        return floor;
    }

    public void setFloor(Integer floor) {
        this.floor = floor;
    }

    public Boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(Boolean available) {
        isAvailable = available;
    }

    public Integer getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(Integer numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }
}