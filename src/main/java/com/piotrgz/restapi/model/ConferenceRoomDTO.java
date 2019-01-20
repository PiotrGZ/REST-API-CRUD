package com.piotrgz.restapi.model;

import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.*;

public class ConferenceRoomDTO {

    @NotBlank(message = "Valid name shouldn't be empty and consist only white characters")
    @Size(min = 2, max = 20, message = "Name should be between 2 and 20 characters")
    private String name;
    @NotNull(message = "Please provide floor number from 0 to 10")
    @Max(value = 10, message = "Floor number must be less or equal 10")
    @Min(value = 0, message = "Floor number must be greater or equal 0")
    private Integer floor;
    private Boolean available;
    @NotNull
    private Integer numberOfSeats;
    private Integer numberOfStandingPlace;
    private Integer numberOfLyingPlace;
    private String projector;
    private Boolean isPhonePresent;
    @Range(min = 0, max = 99, message = "Internal phone number must be in range of 0 and 99")
    private Integer internalPhoneNumber;
    @Pattern(regexp = "\\++[0-9]{2}+\\s+[0-9]{9}", message = "External phone number must be in format +12 123456789")
    private String externalPhoneNumber;
    @Pattern(regexp = "USB|bluetooth", message = "Available communication interfaces are USB or bluetooth")
    private String communicationInterface;

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
