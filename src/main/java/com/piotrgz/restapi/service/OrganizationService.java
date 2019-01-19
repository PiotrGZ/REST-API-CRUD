package com.piotrgz.restapi.service;

import com.piotrgz.restapi.model.Organization;
import com.piotrgz.restapi.repository.OrganizationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;


@Service
public class OrganizationService {

    private OrganizationRepo organizationRepo;

    @Autowired
    public OrganizationService(OrganizationRepo organizationRepository) {
        this.organizationRepo = Objects.requireNonNull(organizationRepository);
    }

    public Organization save(Organization organization) throws MyValidationException {

        if (organizationRepo.findById(organization.getName()).isPresent()) {
            throw new MyValidationException("Organization with name " + organization.getName() + " already exists!");
        }
        return organizationRepo.save(organization);
    }

    public Iterable<Organization> getAll() {
        return organizationRepo.findAll();
    }


    public Organization findByName(String name) throws MyValidationException {
        return organizationRepo.findById(name).orElseThrow(() -> new MyValidationException("Organization " + name + " has not been found"));
    }


    public Organization update(String name, Organization organization) throws MyValidationException {

        if (organizationRepo.findById(organization.getName()).isPresent()) {
            throw new MyValidationException("Organization with name " + name + " already exists!");
        }

        Organization organizationToUpdate = findByName(name);

        organizationToUpdate.setName(organization.getName());
        return organizationRepo.save(organizationToUpdate);
    }


    public void delete(String name) throws MyValidationException {

        organizationRepo.delete(findByName(name));
    }
}
