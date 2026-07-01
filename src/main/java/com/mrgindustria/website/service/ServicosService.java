package com.mrgindustria.website.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.mrgindustria.website.model.Servicos;
import com.mrgindustria.website.repository.ServicosRepository;
import com.mrgindustria.website.utils.ValidationUtils;

@Service
@Transactional
public class ServicosService {

    private final ServicosRepository servicosRepository;
    private final FileUploadService fileUploadService;

    public ServicosService(ServicosRepository servicosRepository, FileUploadService fileUploadService) {
        this.servicosRepository = servicosRepository;
        this.fileUploadService = fileUploadService;
    }

    public Servicos create(Servicos servico, MultipartFile imagemFile) {

        ValidationUtils.validarCampoObrigatorio(servico.getTitulo(), "Título é obrigatório");
        ValidationUtils.validarCampoObrigatorio(servico.getCategoria(), "Categoria é obrigatória");
        ValidationUtils.validarCampoObrigatorio(servico.getDescricao(), "Descrição é obrigatória");
        ValidationUtils.validarCampoObrigatorio(imagemFile, "Imagem é obrigatória");

        if (imagemFile != null && !imagemFile.isEmpty()) {
            String imagemPath = fileUploadService.salvarImagem(imagemFile);
            servico.setImagem(imagemPath);
        }

        UUID servicoId = servico.getId();
        if (servicoId == null) {
            servico.setId(UUID.randomUUID());
        }
        servico.setNew(true);

        return servicosRepository.save(servico);

    }

    public Servicos update(UUID id, Servicos servico, MultipartFile imagemFile) {

        ValidationUtils.validarCampoObrigatorio(servico.getTitulo(), "Título é obrigatório");
        ValidationUtils.validarCampoObrigatorio(servico.getCategoria(), "Categoria é obrigatória");
        ValidationUtils.validarCampoObrigatorio(servico.getDescricao(), "Descrição é obrigatória");

        Servicos existingServico = servicosRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Serviço não encontrado com o ID: " + id));

        existingServico.setTitulo(servico.getTitulo());
        existingServico.setCategoria(servico.getCategoria());
        existingServico.setDescricao(servico.getDescricao());

        if (imagemFile != null && !imagemFile.isEmpty()) {
            String imagemPath = fileUploadService.salvarImagem(imagemFile);
            existingServico.setImagem(imagemPath);
        }

        existingServico.setNew(false);
        return servicosRepository.save(existingServico);
    }

    public Optional<Servicos> findById(UUID id) {
        ValidationUtils.validarCampoObrigatorio(id, "ID do serviço é obrigatório");
        return servicosRepository.findById(id);
    }

    public Iterable<Servicos> findAll() {
        return servicosRepository.findAll();
    }

    public void deleteById(UUID id) {
        ValidationUtils.validarCampoObrigatorio(id, "ID do serviço é obrigatório");
        Servicos existingServico = servicosRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Serviço não encontrado com o ID: " + id));
        if (existingServico.getImagem() != null && !existingServico.getImagem().isEmpty()) {
            fileUploadService.removerImagem(existingServico.getImagem());
        }
        servicosRepository.deleteById(id);
    }


}
