package com.piotrgz.restapi.service;

import com.piotrgz.restapi.model.Organization;
import com.piotrgz.restapi.modelDTO.OrganizationDTO;
import com.piotrgz.restapi.repository.OrganizationRepo;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Service
public class OrganizationService {

    private OrganizationRepo organizationRepo;

    @Autowired
    public OrganizationService(OrganizationRepo organizationRepository) {
        this.organizationRepo = Objects.requireNonNull(organizationRepository);
    }

    public Organization save(Organization organization) throws ValidationException {

        if (!organizationRepo.findById(organization.getName()).isPresent()) {
            return organizationRepo.save(organization);
        }
        throw new ValidationException("Organization with name " + organization.getName() + " already exists!");
    }

    public Iterable<Organization> getAll() {
        return organizationRepo.findAll();
    }


    public Organization findByName(String name) throws ValidationException {
        return organizationRepo.findById(name).orElseThrow(() -> new ValidationException("Organization " + name + " has not been found"));
    }


    public Organization update(String name, Organization organization) throws ValidationException {

        if (!organizationRepo.findById(organization.getName()).isPresent()) {
            throw new ValidationException("Organization " + name + " already exists!");
        }

        Organization organizationToUpdate = organizationRepo.findById(name).orElseThrow(() -> new ValidationException("Organization " + name + " has not been found"));

        organizationToUpdate.setName(organization.getName());
        return organizationRepo.save(organizationToUpdate);
    }


    public void delete(String name) throws ValidationException {

        organizationRepo.delete(organizationRepo.findById(name).orElseThrow(() -> new ValidationException("Organization " + name + " has not been found")));
    }

}
