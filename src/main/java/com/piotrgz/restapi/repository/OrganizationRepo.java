package com.piotrgz.restapi.repository;


import com.piotrgz.restapi.model.Organization;
import org.springframework.data.repository.CrudRepository;


import java.util.List;
import java.util.Optional;

public interface OrganizationRepo extends CrudRepository<Organization, Integer> {
    List<Organization> findAll();
    Optional<Organization> findByName(String name);
}