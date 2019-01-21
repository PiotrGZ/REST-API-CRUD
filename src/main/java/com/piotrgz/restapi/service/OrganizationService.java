package com.piotrgz.restapi.service;

import com.google.common.collect.Lists;
import com.piotrgz.restapi.entity.Organization;
import com.piotrgz.restapi.exceptions.EntityAlreadyExistsException;
import com.piotrgz.restapi.exceptions.EntityNotFoundException;
import com.piotrgz.restapi.model.OrganizationDTO;
import com.piotrgz.restapi.repository.OrganizationRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class OrganizationService {

    private final OrganizationRepository organizationRepository;

    private OrganizationService(OrganizationRepository organizationRepository) {
        this.organizationRepository = Objects.requireNonNull(organizationRepository);
    }

    public OrganizationDTO save(OrganizationDTO organizationDTO) {
        if (organizationRepository.findById(organizationDTO.getName()).isPresent()) {
            throw new EntityAlreadyExistsException("Organization with name: " + organizationDTO.getName() + " already exists");
        }
        return convertToDto(organizationRepository.save(convertToEntity(organizationDTO)));
    }

    public List<OrganizationDTO> getAll() {
        return Lists.newArrayList(organizationRepository.findAll()).stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public OrganizationDTO findByName(String name) {
        return convertToDto(organizationRepository.findById(name).orElseThrow(() -> new EntityNotFoundException("Organization with name: " + name + " was not found")));
    }

    public OrganizationDTO update(String name, OrganizationDTO organizationDTO) {
        if (organizationRepository.findById(organizationDTO.getName()).isPresent()) {
            throw new EntityAlreadyExistsException("Organization with name " + name + " already exists!");
        }

        Organization organizationToUpdate = convertToEntity(findByName(name));
        BeanUtils.copyProperties(organizationDTO, organizationToUpdate);

        return convertToDto(organizationRepository.save(organizationToUpdate));
    }

    public void delete(String name) {
        organizationRepository.delete(convertToEntity(findByName(name)));
    }

    private OrganizationDTO convertToDto(Organization organization) {
        OrganizationDTO organizationDTO = new OrganizationDTO();
        BeanUtils.copyProperties(organization, organizationDTO);
        return organizationDTO;
    }

    private Organization convertToEntity(OrganizationDTO organizationDTO) {
        Organization organization = new Organization();
        BeanUtils.copyProperties(organizationDTO, organization);
        return organization;
    }
}
