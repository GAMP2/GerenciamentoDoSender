package com.example.demo.service;

import com.example.demo.dto.request.IntegracoesExternasRequest;
import com.example.demo.dto.response.IntegracoesExternasResponse;
import com.example.demo.entity.IntegracoesExternas;
import com.example.demo.entity.Usuario;
import com.example.demo.repository.IntegracoesExternasRepository;
import com.example.demo.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class IntegracoesExternasService {

    private final IntegracoesExternasRepository integracoesRepository;
    private final UsuarioRepository usuarioRepository;

    public IntegracoesExternasService(IntegracoesExternasRepository integracoesRepository,
                                      UsuarioRepository usuarioRepository) {
        this.integracoesRepository = integracoesRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional
    public IntegracoesExternasResponse salvar(IntegracoesExternasRequest request) {
        Usuario usuario = usuarioRepository.findById(request.usuarioId())
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        IntegracoesExternas integracao = integracoesRepository
                .findByUsuarioIdAndProvedor(request.usuarioId(), request.provedor())
                .orElseGet(IntegracoesExternas::new);

        integracao.setUsuario(usuario);
        integracao.setProvedor(request.provedor());
        integracao.setIdExterno(request.idExterno());
        integracao.setTokenAcesso(request.tokenAcesso());
        integracao.setTokenAtualizacao(request.tokenAtualizacao());
        integracao.setDataExpiracao(request.dataExpiracao());

        if (integracao.getId() == null) {
            integracao.setDataCriacao(LocalDate.now());
        }

        IntegracoesExternas salva = integracoesRepository.save(integracao);
        return toResponse(salva);
    }

    @Transactional(readOnly = true)
    public List<IntegracoesExternasResponse> listarPorUsuario(UUID usuarioId) {
        return integracoesRepository.findByUsuarioId(usuarioId).stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional
    public void deletar(UUID id) {
        if (!integracoesRepository.existsById(id)) {
            throw new IllegalArgumentException("Integração não encontrada");
        }
        integracoesRepository.deleteById(id);
    }

    private IntegracoesExternasResponse toResponse(IntegracoesExternas i) {
        return new IntegracoesExternasResponse(
                i.getId(),
                i.getUsuario().getId(),
                i.getProvedor(),
                i.getIdExterno(),
                i.getTokenAcesso(),
                i.getTokenAtualizacao(),
                i.getDataExpiracao(),
                i.getDataCriacao()
        );
    }
}