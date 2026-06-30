package com.mrgindustria.website.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.mrgindustria.website.model.Admin;

@Repository
public interface AdminRepository extends CrudRepository<Admin, UUID> {
    
    Optional<Admin> findByUsername(String username);

}
