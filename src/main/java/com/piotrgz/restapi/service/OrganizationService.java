package com.piotrgz.restapi.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.piotrgz.restapi.exceptions.MyEntityAlreadyExistsException;
import com.piotrgz.restapi.entity.Organization;
import com.piotrgz.restapi.model.OrganizationDTO;
import com.piotrgz.restapi.repository.OrganizationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Objects;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;


@Service
public class OrganizationService {

    private OrganizationRepo organizationRepo;
    private ObjectMapper objectMapper;

    @Autowired
    public OrganizationService(OrganizationRepo organizationRepository, ObjectMapper objectMapper) {
        this.organizationRepo = Objects.requireNonNull(organizationRepository);
        this.objectMapper = Objects.requireNonNull(objectMapper);
    }

    public OrganizationDTO save(OrganizationDTO organizationDTO) throws IllegalArgumentException {

        if (organizationRepo.findById(organizationDTO.getName()).isPresent()) {
            throw new MyEntityAlreadyExistsException("Organization with name: " + organizationDTO.getName() + " already exists");
        }
        return convertToDto(organizationRepo.save(convertToEntity(organizationDTO)));
    }

    public Iterable<OrganizationDTO> getAll() {
        Stream<OrganizationDTO> stream = StreamSupport.stream(organizationRepo.findAll().spliterator(), false)
                .map(organization -> convertToDto(organization));

        return stream::iterator;
    }


    public OrganizationDTO findByName(String name) throws IllegalArgumentException {
        return convertToDto(organizationRepo.findById(name).orElseThrow(() -> new EntityNotFoundException("Organization with name: " + name + " was not found")));
    }


    public OrganizationDTO update(String name, OrganizationDTO organizationDTO) throws IllegalArgumentException {

        if (organizationRepo.findById(organizationDTO.getName()).isPresent()) {
            throw new MyEntityAlreadyExistsException("Organization with name " + name + " already exists!");
        }

        Organization organizationToUpdate = convertToEntity(findByName(name));

        organizationToUpdate.setName(organizationDTO.getName());
        return convertToDto(organizationRepo.save(organizationToUpdate));
    }


    public void delete(String name) throws IllegalArgumentException {

        organizationRepo.delete(convertToEntity(findByName(name)));
    }

    private OrganizationDTO convertToDto(Organization organization) {
        return objectMapper.convertValue(organization, OrganizationDTO.class);
    }

    private Organization convertToEntity(OrganizationDTO organizationDTO) {
        return objectMapper.convertValue(organizationDTO, Organization.class);
    }
}
