package com.example.demo.service;

import com.example.demo.dto.request.CurtidaRequest;
import com.example.demo.dto.response.CurtidaResponse;
import com.example.demo.dto.response.CurtidaStatusResponse;
import com.example.demo.mapper.CurtidaMapper;
import com.example.demo.model.Curtidas;
import com.example.demo.model.Usuario;
import com.example.demo.repository.CurtidasRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CurtidaService {

    private final CurtidasRepository curtidasRepository;
    private final UsuarioService usuarioService;
    private final CurtidaMapper curtidaMapper;

    public CurtidaService(CurtidasRepository curtidasRepository,
                          UsuarioService usuarioService,
                          CurtidaMapper curtidaMapper) {
        this.curtidasRepository = curtidasRepository;
        this.usuarioService = usuarioService;
        this.curtidaMapper = curtidaMapper;
    }

    // O mesmo botão resolve "curtir" e "descurtir"
    @Transactional
    public CurtidaStatusResponse alternarCurtida(CurtidaRequest request) {
        UUID idOrigem = request.idOrigem();
        UUID idDestino = request.idDestino();

        if (idOrigem.equals(idDestino)) {
            throw new RuntimeException("Um usuário não pode curtir a si mesmo!");
        }

        Usuario origem = usuarioService.buscarEntidade(idOrigem);
        Usuario destino = usuarioService.buscarEntidade(idDestino);

        Optional<Curtidas> existente =
                curtidasRepository.findByUsuarioOrigemAndUsuarioDestino(origem, destino);

        if (existente.isPresent()) {
            curtidasRepository.delete(existente.get());
            return new CurtidaStatusResponse(false, "Curtida removida com sucesso!");
        }

        Curtidas novaCurtida = new Curtidas();
        novaCurtida.setUsuarioOrigem(origem);
        novaCurtida.setUsuarioDestino(destino);
        curtidasRepository.save(novaCurtida);
        return new CurtidaStatusResponse(true, "Usuário curtido com sucesso!");
    }

    @Transactional(readOnly = true)
    public long contarCurtidasRecebidas(UUID idDestino) {
        Usuario destino = usuarioService.buscarEntidade(idDestino);
        return curtidasRepository.countByUsuarioDestino(destino);
    }

    @Transactional(readOnly = true)
    public List<CurtidaResponse> listarCurtidasRecebidas(UUID idDestino) {
        Usuario destino = usuarioService.buscarEntidade(idDestino);
        return curtidasRepository.findByUsuarioDestino(destino).stream()
                .map(curtidaMapper::toResponse)
                .toList();
    }
}