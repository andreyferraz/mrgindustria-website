package com.mrgindustria.website.service;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mrgindustria.website.model.Areas;
import com.mrgindustria.website.repository.AreasRepository;
import com.mrgindustria.website.utils.ValidationUtils;

@Service
@Transactional
public class AreasService {

    private final AreasRepository areasRepository;

    public AreasService(AreasRepository areasRepository) {
        this.areasRepository = areasRepository;
    }

    public Areas create(Areas area) {
        ValidationUtils.validarCampoObrigatorio(area.getTitulo(), "Título é obrigatório");
        ValidationUtils.validarCampoObrigatorio(area.getDescricao(), "Descrição é obrigatória");
        UUID areaId = area.getId();
        if (areaId == null) {
            area.setId(UUID.randomUUID());
        }
        area.setNew(true);
        return areasRepository.save(area);
    }

    public Areas update(UUID id, Areas area) {
        ValidationUtils.validarCampoObrigatorio(area.getTitulo(), "Título é obrigatório");
        ValidationUtils.validarCampoObrigatorio(area.getDescricao(), "Descrição é obrigatória");

        Areas existingArea = areasRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Área não encontrada com o ID: " + id));

        existingArea.setTitulo(area.getTitulo());
        existingArea.setDescricao(area.getDescricao());

        existingArea.setNew(false);
        return areasRepository.save(existingArea);
    }

    public void delete(UUID id) {
        Areas existingArea = areasRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Área não encontrada com o ID: " + id));
        areasRepository.delete(existingArea);
    }

    public Iterable<Areas> findAll() {
        return areasRepository.findAll();
    }

}
