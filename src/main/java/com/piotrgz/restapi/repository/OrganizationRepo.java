package com.piotrgz.restapi.repository;


import com.piotrgz.restapi.model.Organization;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

@Repository
public interface OrganizationRepo extends CrudRepository<Organization, String> {
}