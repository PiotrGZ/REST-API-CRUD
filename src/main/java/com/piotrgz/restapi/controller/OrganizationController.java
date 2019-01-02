package com.piotrgz.restapi.controller;


import com.piotrgz.restapi.model.Organization;

import com.piotrgz.restapi.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public void save(@RequestBody Organization organization) {
        organizationService.save(organization);
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
}
