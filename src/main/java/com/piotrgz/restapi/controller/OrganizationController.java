package com.piotrgz.restapi.controller;


import com.piotrgz.restapi.model.Organization;

import com.piotrgz.restapi.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;


@RestController
@RequestMapping("/organization")
public class OrganizationController {

    private OrganizationService organizationService;

    @Autowired
    public OrganizationController(OrganizationService organizationService) {
        this.organizationService = Objects.requireNonNull(organizationService);
    }

    @PostMapping
    public ResponseEntity save(@Valid @RequestBody Organization organization) {

        if (isNameValid(organization.getName()))
            organizationService.save(organization);
        return ResponseEntity.ok().build();
    }

    @GetMapping

    public ResponseEntity<List<Organization>> getAll() {
        return ResponseEntity.ok(organizationService.getAll());
    }

    @DeleteMapping
    public ResponseEntity delete(@RequestParam int id) {
        if (isOrganizationPresent(id)) {
            organizationService.delete(id);
            return ResponseEntity.ok().build();
        } else
            return ResponseEntity.badRequest().body("Organization with id " + id + " has not been found");
    }


    @PatchMapping
    public ResponseEntity update(@RequestParam int id, @Valid @RequestBody Organization organization) {
        if (isOrganizationPresent(id) && isNameValid(organization.getName())) {
            organizationService.update(id, organization);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().body("Organization with id " + id + " has not been found, or name is not valid");
        }
    }

    private boolean isOrganizationPresent(int id) {
        return organizationService.getAll().stream().anyMatch(t -> ((Integer) t.getId()).equals(id));
    }

    private boolean isNameValid(String name) {
        return !name.trim().isEmpty()
                && !organizationService.getAll().stream().anyMatch(t -> t.getName().equals(name));
    }
}
