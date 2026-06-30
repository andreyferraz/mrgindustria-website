package com.mrgindustria.website.repository;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.mrgindustria.website.model.Marcas;

@Repository
public interface MarcasRepository extends CrudRepository<Marcas, UUID> {

}
