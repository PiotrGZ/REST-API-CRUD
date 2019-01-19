package com.piotrgz.restapi.controller;


import com.piotrgz.restapi.model.Organization;

import com.piotrgz.restapi.modelDTO.OrganizationDTO;
import com.piotrgz.restapi.service.OrganizationService;
import com.piotrgz.restapi.service.MyValidationException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Objects;


@RestController
@RequestMapping("/organizations")
public class OrganizationController {

    private OrganizationService organizationService;
    private ModelMapper modelMapper;

    @Autowired
    public OrganizationController(OrganizationService organizationService, ModelMapper modelMapper) {
        this.organizationService = Objects.requireNonNull(organizationService);
        this.modelMapper=Objects.requireNonNull(modelMapper);
    }

    @PostMapping
    public ResponseEntity save(@Valid @RequestBody OrganizationDTO organizationDTO) {
        try {
            return ResponseEntity.ok(organizationService.save(convertToEntity(organizationDTO)));
        } catch (MyValidationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<Iterable<Organization>> getAll() {
        return ResponseEntity.ok(organizationService.getAll());
    }

    @GetMapping("/{name}")
    public ResponseEntity findByName(@PathVariable("name") String name) {
        try {
            return ResponseEntity.ok(convertToDto(organizationService.findByName(name)));
        } catch (MyValidationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @DeleteMapping("/{name}")
    public ResponseEntity delete(@PathVariable("name") String name) {
        try {
            organizationService.delete(name);
            return ResponseEntity.ok().build();
        } catch (MyValidationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @PutMapping("/{name}")
    public ResponseEntity update(@PathVariable String name, @Valid @RequestBody OrganizationDTO organizationDTO) {
        try {
            return ResponseEntity.ok(convertToDto(organizationService.update(name, convertToEntity(organizationDTO))));
        } catch (MyValidationException e) {
           return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    private OrganizationDTO convertToDto(Organization organization) {
        return modelMapper.map(organization, OrganizationDTO.class);
    }

    private Organization convertToEntity(OrganizationDTO organizationDTO){
        return modelMapper.map(organizationDTO, Organization.class);
    }
}

