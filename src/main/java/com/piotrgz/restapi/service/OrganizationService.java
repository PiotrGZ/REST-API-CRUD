package com.piotrgz.restapi.service;

import com.piotrgz.restapi.model.Organization;
import com.piotrgz.restapi.repository.OrganizationRepo;
import org.springframework.beans.factory.annotation.Autowired;
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

    public void save(Organization organization) {
        organizationRepo.save(organization);
    }

    public List<Organization> getAll() {
        return organizationRepo.findAll();
    }

    public void update(int id, Organization organization) {
        Organization toUpdate = organizationRepo.findById(id).get();
        toUpdate.setName(organization.getName());
        toUpdate.setConferenceRoomReservationCollection(organization.getConferenceRoomReservationCollection());
        organizationRepo.save(toUpdate);
    }

    public void delete(int id) {
        organizationRepo.deleteById(id);
    }
}
