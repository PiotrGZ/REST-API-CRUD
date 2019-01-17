package com.piotrgz.restapi.model;



import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
public class ConferenceRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @NotBlank(message = "Valid name shouldn't be empty and consist only white characters")
    @Size(min = 2, max = 20, message = "Name should be between 2 and 20 characters")
    private String name;
    @NotNull(message = "Please provide floor number from 0 to 10")
    @Max(value = 10, message = "Floor number must be less or equal 10")
    @Min(value = 0, message = "Floor number must be greater or equal 0")
    private int floor;
    private boolean available;
    @NotNull
    private int numberOfSeats;
    private int numberOfStandingPlace;
    private int numberOfLyingPlace;
    private String projector;
    private boolean isPhonePresent;
    @Range(min = 0, max = 99, message = "Internal phone number must be in range of 0 and 99")
    private int internalPhoneNumber;
    @Pattern(regexp = "\\++[0-9]{2}+\\s+[0-9]{9}", message = "External phone number must be in format +12 123456789")
    private String externalPhoneNumber;
    @Pattern(regexp = "USB|bluetooth", message = "Available communication interfaces are USB or bluetooth")
    private String communicationInterface;


    public ConferenceRoom() {
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