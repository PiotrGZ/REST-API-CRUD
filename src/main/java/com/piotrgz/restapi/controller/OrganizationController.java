package com.piotrgz.restapi.controller;

import com.piotrgz.restapi.model.OrganizationDTO;
import com.piotrgz.restapi.service.OrganizationService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/organizations")
class OrganizationController {
    private final OrganizationService organizationService;

    private OrganizationController(OrganizationService organizationService) {
        this.organizationService = Objects.requireNonNull(organizationService);
    }

    @PostMapping
    private OrganizationDTO save(@Valid @RequestBody OrganizationDTO organizationDTO) {
        return organizationService.save(organizationDTO);
    }

    @GetMapping
    private List<OrganizationDTO> getAll() {
        return organizationService.getAll();
    }

    @GetMapping("/{name}")
    private OrganizationDTO findByName(@PathVariable("name") String name) {
        return (organizationService.findByName(name));
    }

    @DeleteMapping("/{name}")
    private void delete(@PathVariable("name") String name) {
        organizationService.delete(name);
    }

    @PutMapping("/{name}")
    private OrganizationDTO update(@PathVariable String name, @Valid @RequestBody OrganizationDTO organizationDTO) {
        return (organizationService.update(name, organizationDTO));
    }
}

