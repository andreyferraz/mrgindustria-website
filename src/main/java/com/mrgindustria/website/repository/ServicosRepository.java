package com.mrgindustria.website.repository;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.mrgindustria.website.model.Servicos;

@Repository
public interface ServicosRepository extends CrudRepository<Servicos, UUID> {

}
