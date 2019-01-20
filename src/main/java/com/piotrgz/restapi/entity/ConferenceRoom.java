package com.piotrgz.restapi.entity;

import javax.persistence.*;

@Entity
public class ConferenceRoom {

    @Id
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private Integer floor;
    private Boolean available;
    @Column(nullable = false)
    private Integer numberOfSeats;
    private Integer numberOfStandingPlace;
    private Integer numberOfLyingPlace;
    private String projector;
    private Boolean isPhonePresent;
    private Integer internalPhoneNumber;
    private String externalPhoneNumber;
    private String communicationInterface;

    public ConferenceRoom() {
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

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public Integer getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(Integer numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public Integer getNumberOfStandingPlace() {
        return numberOfStandingPlace;
    }

    public void setNumberOfStandingPlace(Integer numberOfStandingPlace) {
        this.numberOfStandingPlace = numberOfStandingPlace;
    }

    public Integer getNumberOfLyingPlace() {
        return numberOfLyingPlace;
    }

    public void setNumberOfLyingPlace(Integer numberOfLyingPlace) {
        this.numberOfLyingPlace = numberOfLyingPlace;
    }

    public String getProjector() {
        return projector;
    }

    public void setProjector(String projector) {
        this.projector = projector;
    }

    public Boolean getPhonePresent() {
        return isPhonePresent;
    }

    public void setPhonePresent(Boolean phonePresent) {
        isPhonePresent = phonePresent;
    }

    public Integer getInternalPhoneNumber() {
        return internalPhoneNumber;
    }

    public void setInternalPhoneNumber(Integer internalPhoneNumber) {
        this.internalPhoneNumber = internalPhoneNumber;
    }

    public String getExternalPhoneNumber() {
        return externalPhoneNumber;
    }

    public void setExternalPhoneNumber(String externalPhoneNumber) {
        this.externalPhoneNumber = externalPhoneNumber;
    }

    public String getCommunicationInterface() {
        return communicationInterface;
    }

    public void setCommunicationInterface(String communicationInterface) {
        this.communicationInterface = communicationInterface;
    }
}