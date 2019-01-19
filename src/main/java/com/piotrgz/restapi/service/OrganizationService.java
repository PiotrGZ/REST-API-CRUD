package com.piotrgz.restapi.service;


import com.piotrgz.restapi.exceptions.MyEntityAlreadyExistsException;
import com.piotrgz.restapi.model.Organization;
import com.piotrgz.restapi.repository.OrganizationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Objects;


@Service
public class OrganizationService {

    private OrganizationRepo organizationRepo;

    @Autowired
    public OrganizationService(OrganizationRepo organizationRepository) {
        this.organizationRepo = Objects.requireNonNull(organizationRepository);
    }

    public Organization save(Organization organization) throws IllegalArgumentException {

        if (organizationRepo.findById(organization.getName()).isPresent()) {
            throw new MyEntityAlreadyExistsException("Organization with name: " + organization.getName() + " already exists");
        }
        return organizationRepo.save(organization);
    }

    public Iterable<Organization> getAll() {
        return organizationRepo.findAll();
    }


    public Organization findByName(String name) throws IllegalArgumentException {
        return organizationRepo.findById(name).orElseThrow(() -> new EntityNotFoundException("Organization with name: " + name + " was not found"));
    }


    public Organization update(String name, Organization organization) throws IllegalArgumentException {

        if (organizationRepo.findById(organization.getName()).isPresent()) {
            throw new MyEntityAlreadyExistsException("Organization with name " + name + " already exists!");
        }

        Organization organizationToUpdate = findByName(name);

        organizationToUpdate.setName(organization.getName());
        return organizationRepo.save(organizationToUpdate);
    }


    public void delete(String name) throws IllegalArgumentException {

        organizationRepo.delete(findByName(name));
    }
}
