package com.example.demo.service;

import com.example.demo.dto.request.DenunciaRequest;
import com.example.demo.dto.request.SuspensoesRequest;
import com.example.demo.dto.response.DenunciaResponse;
import com.example.demo.entity.Denuncia;
import com.example.demo.entity.Usuario;
import com.example.demo.mapper.DenunciaMapper;
import com.example.demo.repository.DenunciaRepository;
import com.example.demo.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.UUID;

@Service
public class DenunciaService {
    private final DenunciaRepository denunciaRepository;
    private final UsuarioRepository usuarioRepository;
    private final DenunciaMapper denunciaMapper;
    private final SuspensoesService suspensoesService;


    public DenunciaService(DenunciaRepository denunciaRepository, UsuarioRepository usuarioRepository, DenunciaMapper denunciaMapper, SuspensoesService suspensoesService) {
        this.denunciaRepository = denunciaRepository;
        this.usuarioRepository = usuarioRepository;
        this.denunciaMapper = denunciaMapper;
        this.suspensoesService = suspensoesService;
    }

    @Transactional
    public DenunciaResponse registrar(DenunciaRequest request) {
        if (request.idDenunciante().equals(request.idDenunciado())) {
            throw new IllegalArgumentException("Um usuário não pode anunciar a si mesmo.");
        }
        Usuario denunciante = usuarioRepository.findById(request.idDenunciante()).orElseThrow(() -> new IllegalArgumentException("Usuário denunciante não encontrado."));
        Usuario denunciado = usuarioRepository.findById(request.idDenunciado()).orElseThrow(() -> new IllegalArgumentException("Usuário denunciado não encontrado."));

        Denuncia novaDenuncia = denunciaMapper.toEntity(request, denunciante, denunciado);
        Denuncia salva = denunciaRepository.save(novaDenuncia);

        List<Denuncia> historico = denunciaRepository.findByDenunciadoIdOrderByCriadaEmDesc(denunciado.getId());

        if (historico.size() >= 3) { //Se atingir 3 ou mais denúncias

            SuspensoesRequest suspensaoAutomatico = new SuspensoesRequest(
                    denunciado.getId(),
                    "Conta suspensa automaticamente após acumular " + historico.size() + " denúncias no sistema.",
                    null,
                    null,
                    "SUSPENSÃO AUTOMÁTICA POR SISTEMA"
            );
            suspensoesService.solicitarSuspensao(suspensaoAutomatico);
        }

        return denunciaMapper.toResponse(salva);
    }




    @Transactional(readOnly = true)
    public List<DenunciaResponse> listarTodas() {
        return denunciaRepository.findAll().stream().map(denunciaMapper::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public List<DenunciaResponse> listarPorStatus(String status) {
        return denunciaRepository.findByStatus(status).stream().map(denunciaMapper::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public List<DenunciaResponse> historicoDoDenunciado(UUID idDenunciado) {
        return denunciaRepository.findByDenunciadoIdOrderByCriadaEmDesc(idDenunciado).stream().map(denunciaMapper::toResponse).toList();
    }


}
