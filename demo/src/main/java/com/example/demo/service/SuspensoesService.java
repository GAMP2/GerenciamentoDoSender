package com.example.demo.service;

import com.example.demo.dto.request.SuspensoesRequest;
import com.example.demo.dto.response.SuspensoesResponse;
import com.example.demo.entity.Suspensoes;
import com.example.demo.entity.Usuario;
import com.example.demo.repository.SuspensoesRepository;
import com.example.demo.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class SuspensoesService {

    private final SuspensoesRepository suspensoesRepository;
    private final UsuarioRepository usuarioRepository;

    public SuspensoesService(SuspensoesRepository suspensoesRepository,
                             UsuarioRepository usuarioRepository) {
        this.suspensoesRepository = suspensoesRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional
    public SuspensoesResponse solicitarSuspensao(SuspensoesRequest request) {
        Usuario usuario = usuarioRepository.findById(request.usuarioId())
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        Suspensoes suspensao = new Suspensoes();
        suspensao.setUsuario(usuario);
        suspensao.setMotivo(request.motivo());
        suspensao.setStatus(true);
        suspensao.setDataSolicitacao(LocalDate.now());
        suspensao.setDataRevisao(request.dataRevisao());
        suspensao.setDataEntrega(request.dataEntrega());
        suspensao.setObservacaoDecisao(request.observacaoDecisao());

        Suspensoes salva = suspensoesRepository.save(suspensao);
        return toResponse(salva);
    }

    @Transactional(readOnly = true)
    public List<SuspensoesResponse> listarPorUsuario(UUID usuarioId) {
        return suspensoesRepository.findByUsuarioId(usuarioId).stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<SuspensoesResponse> listarTodas() {
        return suspensoesRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    private SuspensoesResponse toResponse(Suspensoes s) {
        return new SuspensoesResponse(
                s.getId(),
                s.getUsuario().getId(),
                s.getMotivo(),
                s.isStatus(),
                s.getDataSolicitacao(),
                s.getDataRevisao(),
                s.getDataEntrega(),
                s.getObservacaoDecisao()
        );
    }
}