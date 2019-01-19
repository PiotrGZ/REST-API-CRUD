package com.piotrgz.restapi.controller;


import com.piotrgz.restapi.entity.Organization;

import com.piotrgz.restapi.model.OrganizationDTO;
import com.piotrgz.restapi.service.OrganizationService;
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
        this.modelMapper = Objects.requireNonNull(modelMapper);
    }

    @PostMapping
    public OrganizationDTO save(@Valid @RequestBody OrganizationDTO organizationDTO) throws IllegalArgumentException {
        return convertToDto(organizationService.save(convertToEntity(organizationDTO)));
    }

    @GetMapping
    public ResponseEntity<Iterable<Organization>> getAll() {
        return ResponseEntity.ok(organizationService.getAll());
    }

    @GetMapping("/{name}")
    public OrganizationDTO findByName(@PathVariable("name") String name) throws IllegalArgumentException {
        return convertToDto(organizationService.findByName(name));
    }


    @DeleteMapping("/{name}")
    public void delete(@PathVariable("name") String name) throws IllegalArgumentException {
        organizationService.delete(name);
    }


    @PutMapping("/{name}")
    public OrganizationDTO update(@PathVariable String name, @Valid @RequestBody OrganizationDTO organizationDTO) throws IllegalArgumentException {
        return convertToDto(organizationService.update(name, convertToEntity(organizationDTO)));
    }

    private OrganizationDTO convertToDto(Organization organization) {
        return modelMapper.map(organization, OrganizationDTO.class);
    }

    private Organization convertToEntity(OrganizationDTO organizationDTO) {
        return modelMapper.map(organizationDTO, Organization.class);
    }
}

