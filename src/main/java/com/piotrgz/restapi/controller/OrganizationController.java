package com.piotrgz.restapi.controller;


import com.piotrgz.restapi.model.Organization;

import com.piotrgz.restapi.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/organization")
public class OrganizationController {

    public static final int MIN_NAME_LENGTH = 2;
    public static final int MAX_NAME_LENGTH = 20;
    public static final String NOT_VALID_NAME_BY_DEFAULT = "";
    private OrganizationService organizationService;

    @Autowired
    public OrganizationController(OrganizationService organizationService) {
        this.organizationService = Objects.requireNonNull(organizationService);
    }

    @PostMapping
    public ResponseEntity save(@RequestBody Organization organization) {
        Optional<String> organizationNullableName = Optional.ofNullable(organization.getName());
        String organizationName=organizationNullableName.orElse(NOT_VALID_NAME_BY_DEFAULT);
        if (isNameNotUsed(organizationName) && isNameValid(organizationName)) {
            organizationService.save(organization);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Name not valid");
        }
    }

    @GetMapping

    public ResponseEntity<List<Organization>> getAll() {
        return ResponseEntity.ok(organizationService.getAll());
    }

    @DeleteMapping
    public void delete(@RequestParam int id) {
        organizationService.delete(id);
    }

    @PatchMapping
    public void update(@RequestParam int id, @RequestBody Organization organization) {
        organizationService.update(id, organization);
    }


    public boolean isNameNotUsed(String name) {
        List<Organization> organizationList = organizationService.getAll();
        return !organizationList.stream()
                .anyMatch(t -> t.getName().equals(name));
    }

    public boolean isNameValid(String name) {
        if (name.length() < MIN_NAME_LENGTH && name.length() > MAX_NAME_LENGTH)
            return false;
        if (name.trim().isEmpty())
            return false;
        return true;
    }
}
