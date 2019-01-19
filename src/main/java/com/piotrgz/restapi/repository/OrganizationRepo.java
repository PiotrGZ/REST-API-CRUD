package com.piotrgz.restapi.repository;


import com.piotrgz.restapi.entity.Organization;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizationRepo extends CrudRepository<Organization, String> {
}