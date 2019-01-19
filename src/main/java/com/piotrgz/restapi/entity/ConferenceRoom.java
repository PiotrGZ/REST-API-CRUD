package com.piotrgz.restapi.entity;


import javax.persistence.*;


@Entity
public class ConferenceRoom {

    @Id
    private String name;
    private Integer floor;
    private Boolean available;
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

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public int getNumberOfStandingPlace() {
        return numberOfStandingPlace;
    }

    public void setNumberOfStandingPlace(int numberOfStandingPlace) {
        this.numberOfStandingPlace = numberOfStandingPlace;
    }

    public int getNumberOfLyingPlace() {
        return numberOfLyingPlace;
    }

    public void setNumberOfLyingPlace(int numberOfLyingPlace) {
        this.numberOfLyingPlace = numberOfLyingPlace;
    }

    public String getProjector() {
        return projector;
    }

    public void setProjector(String projector) {
        this.projector = projector;
    }

    public boolean isPhonePresent() {
        return isPhonePresent;
    }

    public void setPhonePresent(boolean phonePresent) {
        isPhonePresent = phonePresent;
    }

    public int getInternalPhoneNumber() {
        return internalPhoneNumber;
    }

    public void setInternalPhoneNumber(int internalPhoneNumber) {
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