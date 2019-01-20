package com.piotrgz.restapi.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.piotrgz.restapi.exceptions.EntityAlreadyExistsException;
import com.piotrgz.restapi.entity.Organization;
import com.piotrgz.restapi.exceptions.EntityNotFoundException;
import com.piotrgz.restapi.model.OrganizationDTO;
import com.piotrgz.restapi.repository.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Service
public class OrganizationService {

    private OrganizationRepository organizationRepository;
    private ObjectMapper objectMapper;

    @Autowired
    public OrganizationService(OrganizationRepository organizationRepository, ObjectMapper objectMapper) {
        this.organizationRepository = Objects.requireNonNull(organizationRepository);
        this.objectMapper = Objects.requireNonNull(objectMapper);
    }

    public OrganizationDTO save(OrganizationDTO organizationDTO) {
        if (organizationRepository.findById(organizationDTO.getName()).isPresent()) {
            throw new EntityAlreadyExistsException("Organization with name: " + organizationDTO.getName() + " already exists");
        }
        return convertToDto(organizationRepository.save(convertToEntity(organizationDTO)));
    }

    public Iterable<OrganizationDTO> getAll() {
        Stream<OrganizationDTO> stream = StreamSupport.stream(organizationRepository.findAll().spliterator(), false).map(organization -> convertToDto(organization));
        return stream::iterator;
    }

    public OrganizationDTO findByName(String name) {
        return convertToDto(organizationRepository.findById(name).orElseThrow(() -> new EntityNotFoundException("Organization with name: " + name + " was not found")));
    }

    public OrganizationDTO update(String name, OrganizationDTO organizationDTO) {
        if (organizationRepository.findById(organizationDTO.getName()).isPresent()) {
            throw new EntityAlreadyExistsException("Organization with name " + name + " already exists!");
        }

        Organization organizationToUpdate = convertToEntity(findByName(name));

        organizationToUpdate.setName(organizationDTO.getName());
        return convertToDto(organizationRepository.save(organizationToUpdate));
    }

    public void delete(String name) {
        organizationRepository.delete(convertToEntity(findByName(name)));
    }

    private OrganizationDTO convertToDto(Organization organization) {
        return objectMapper.convertValue(organization, OrganizationDTO.class);
    }

    private Organization convertToEntity(OrganizationDTO organizationDTO) {
        return objectMapper.convertValue(organizationDTO, Organization.class);
    }
}
