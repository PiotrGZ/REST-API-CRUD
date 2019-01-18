package com.piotrgz.restapi.modelDTO;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class OrganizationDTO {

    @NotBlank(message = "Valid name shouldn't be empty and consist only white characters")
    @Size(min = 2, max = 20, message = "Please provide name in range of 2-20 characters")
    private String name;

    public OrganizationDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
