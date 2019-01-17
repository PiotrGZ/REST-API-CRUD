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
        return organizationService.save(organization);
    }

    @GetMapping
    public ResponseEntity<List<Organization>> getAll() {
        return ResponseEntity.ok(organizationService.getAll());
    }

    @GetMapping("/{name}")
    public ResponseEntity<Organization> findByName(@PathVariable("name") String name) {
        return organizationService.findByName(name);
    }


    @DeleteMapping("/{name}")
    public ResponseEntity delete(@PathVariable("name") String name) {
        return organizationService.delete(name);
    }


    @PutMapping("/{name}")
    public ResponseEntity update(@PathVariable String name, @Valid @RequestBody Organization organization) {
        return organizationService.update(name, organization);
    }
}

