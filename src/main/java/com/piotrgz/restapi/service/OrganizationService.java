package com.piotrgz.restapi.service;

import com.piotrgz.restapi.model.Organization;
import com.piotrgz.restapi.repository.OrganizationRepo;
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

    public ResponseEntity save(Organization organization) {

        Optional<Organization> organizationToSave = findByNameInService(organization.getName());

        if (!organizationToSave.isPresent()) {
            organizationRepo.save(organization);
            return ResponseEntity.ok(organization);
        }
        return badRequestNameIsNotUnique(organization.getName());
    }


    public List<Organization> getAll() {
        return organizationRepo.findAll();
    }


    public ResponseEntity findByName(String name) {
        Optional<Organization> organizationToFind = findByNameInService(name);

        if (organizationToFind.isPresent()) {
            return ResponseEntity.ok(organizationToFind.get());
        }
        return badRequestNameNotFond(name);
    }


    public ResponseEntity update(String name, Organization organization) {

        Optional<Organization> organizationToUpdateOpt = findByNameInService(name);

        if (organizationToUpdateOpt.isPresent() && areNamesEqual(organization, organizationToUpdateOpt)) {
            return badRequestNameIsNotUnique(name);
        }


        if (organizationToUpdateOpt.isPresent()) {
            Organization organizationToUpdate = organizationToUpdateOpt.get();
            organizationToUpdate.setName(organization.getName());
            organizationRepo.save(organizationToUpdate);
            return ResponseEntity.ok("Organization " + name + " has been updated");
        }
        return badRequestNameNotFond(name);
    }


    public ResponseEntity delete(String name) {

        Optional<Organization> organizationToDelete = findByNameInService(name);

        if (organizationToDelete.isPresent()) {
            organizationRepo.delete(organizationToDelete.get());
            return ResponseEntity.ok("Organization " + name + " has been deleted");
        }
        return badRequestNameNotFond(name);
    }

    private ResponseEntity badRequestNameNotFond(String name) {
        return ResponseEntity.badRequest().body("Organization " + name + " has not been found");
    }

    private Optional<Organization> findByNameInService(String name) {
        return organizationRepo.findByName(name);
    }

    private boolean areNamesEqual(Organization organization, Optional<Organization> organizationToUpdateOpt) {
        return organizationToUpdateOpt.get().getName().equals(organization.getName());
    }

    private ResponseEntity badRequestNameIsNotUnique(String name) {
        return ResponseEntity.badRequest().body("Organization with name " + name + " already exists!\n");
    }
}
