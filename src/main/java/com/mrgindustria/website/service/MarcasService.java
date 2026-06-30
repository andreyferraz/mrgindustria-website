package com.mrgindustria.website.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.mrgindustria.website.model.Marcas;
import com.mrgindustria.website.repository.MarcasRepository;
import com.mrgindustria.website.utils.ValidationUtils;

@Service
public class MarcasService {

    private final MarcasRepository marcasRepository;
    private final FileUploadService fileUploadService;

    public MarcasService(MarcasRepository marcasRepository, FileUploadService fileUploadService) {
        this.marcasRepository = marcasRepository;
        this.fileUploadService = fileUploadService;
    }

    public Marcas create(Marcas marca, MultipartFile imagemFile) {

        ValidationUtils.validarCampoObrigatorio(marca.getTitulo(), "Título é obrigatório");
        ValidationUtils.validarCampoObrigatorio(marca.getDescricao(), "Descrição é obrigatória");
        ValidationUtils.validarCampoObrigatorio(imagemFile, "Imagem é obrigatória");

        if (imagemFile != null && !imagemFile.isEmpty()) {
            String imagemPath = fileUploadService.salvarImagem(imagemFile);
            marca.setImagem(imagemPath);
        }

        UUID marcaId = marca.getId();
        if (marcaId == null) {
            marca.setId(UUID.randomUUID());
        }
        marca.setNew(true);

        return marcasRepository.save(marca);

    }

    public Marcas update(UUID id, Marcas marca, MultipartFile imagemFile) {

        ValidationUtils.validarCampoObrigatorio(marca.getTitulo(), "Título é obrigatório");
        ValidationUtils.validarCampoObrigatorio(marca.getDescricao(), "Descrição é obrigatória");

        Marcas existingMarca = marcasRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Marca não encontrada com o ID: " + id));

        existingMarca.setTitulo(marca.getTitulo());
        existingMarca.setDescricao(marca.getDescricao());

        if (imagemFile != null && !imagemFile.isEmpty()) {
            String imagemPath = fileUploadService.salvarImagem(imagemFile);
            existingMarca.setImagem(imagemPath);
        }

        existingMarca.setNew(false);
        return marcasRepository.save(existingMarca);
    }

    public Optional<Marcas> findById(UUID id) {
        ValidationUtils.validarCampoObrigatorio(id, "ID da marca é obrigatório");
        return marcasRepository.findById(id);
    }

    public Iterable<Marcas> findAll() {
        return marcasRepository.findAll();
    }

    public void deleteById(UUID id) {
        ValidationUtils.validarCampoObrigatorio(id, "ID da marca é obrigatório");
        Marcas existingMarca = marcasRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Marca não encontrada com o ID: " + id));
        if (existingMarca.getImagem() != null && !existingMarca.getImagem().isEmpty()) {
            fileUploadService.removerImagem(existingMarca.getImagem());
        }
        marcasRepository.deleteById(id);
    }

}
