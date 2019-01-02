package com.piotrgz.restapi.service;

import com.piotrgz.restapi.model.Organization;
import com.piotrgz.restapi.repository.OrganizationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@Service
public class OrganizationService {

    private OrganizationRepo organizationRepo;

    @Autowired
    public OrganizationService(OrganizationRepo organizationRepository) {
        this.organizationRepo = Objects.requireNonNull(organizationRepository);
    }

    public ResponseEntity save(Organization organization) {
        organizationRepo.save(organization);
        return ResponseEntity.ok().build();
    }

    public List<Organization> getAll() {
        return organizationRepo.findAll();
    }

    public ResponseEntity update(int id, Organization organization) {
        Organization toUpdate = organizationRepo.findById(id).get();
        toUpdate.setName(organization.getName());
        toUpdate.setId(organization.getId());
        toUpdate.setConferenceRoomReservationCollection(organization.getConferenceRoomReservationCollection());

        organizationRepo.save(toUpdate);

        return ResponseEntity.ok().build();
    }

    public ResponseEntity delete(int id) {
        organizationRepo.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
