package com.piotrgz.restapi.controller;


import com.piotrgz.restapi.model.OrganizationDTO;
import com.piotrgz.restapi.service.OrganizationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Objects;


@RestController
@RequestMapping("/organizations")
public class OrganizationController {

    private OrganizationService organizationService;

    @Autowired
    public OrganizationController(OrganizationService organizationService) {
        this.organizationService = Objects.requireNonNull(organizationService);
    }

    @PostMapping
    public OrganizationDTO save(@Valid @RequestBody OrganizationDTO organizationDTO) throws IllegalArgumentException {
        return organizationService.save(organizationDTO);
    }

    @GetMapping
    public Iterable<OrganizationDTO> getAll() {
        return organizationService.getAll();
    }

    @GetMapping("/{name}")
    public OrganizationDTO findByName(@PathVariable("name") String name) throws IllegalArgumentException {
        return (organizationService.findByName(name));
    }


    @DeleteMapping("/{name}")
    public void delete(@PathVariable("name") String name) throws IllegalArgumentException {
        organizationService.delete(name);
    }


    @PutMapping("/{name}")
    public OrganizationDTO update(@PathVariable String name, @Valid @RequestBody OrganizationDTO organizationDTO) throws IllegalArgumentException {
        return (organizationService.update(name, organizationDTO));
    }
}

