package com.example.demo.service.interacoes;

import com.example.demo.dto.request.BloqueioRequest;
import com.example.demo.dto.response.BloqueioResponse;
import com.example.demo.mapper.BloqueioMapper;
import com.example.demo.model.Usuario;
import com.example.demo.model.interacoes.Bloqueios;
import com.example.demo.repository.interacoes.BloqueioRepository;
import com.example.demo.repository.interacoes.CurtidasRepository;
import com.example.demo.service.UsuarioService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BloqueioService {

    private final BloqueioRepository bloqueioRepository;
    private final CurtidasRepository curtidaRepository;
    private final UsuarioService usuarioService;
    private final BloqueioMapper bloqueioMapper;

    public BloqueioService(BloqueioRepository bloqueioRepository,
                           CurtidasRepository curtidaRepository,
                           UsuarioService usuarioService,
                           BloqueioMapper bloqueioMapper) {
        this.bloqueioRepository = bloqueioRepository;
        this.curtidaRepository = curtidaRepository;
        this.usuarioService = usuarioService;
        this.bloqueioMapper = bloqueioMapper;
    }

    @Transactional
    public BloqueioResponse bloquearUsuario(BloqueioRequest request) {
        if (request.idBloqueador().equals(request.idBloqueado())) {
            throw new RuntimeException("Não podes bloquear o teu próprio perfil!");
        }

        Usuario bloqueador = usuarioService.buscarEntidade(request.idBloqueador());
        Usuario bloqueado = usuarioService.buscarEntidade(request.idBloqueado());

        if (bloqueioRepository.existsByUsuarioBloqueadorAndUsuarioBloqueado(bloqueador, bloqueado)) {
            throw new RuntimeException("Este utilizador já se encontra bloqueado!");
        }

        // se houver curtida (de A para B ou de B para A), apaga
        curtidaRepository.findByUsuarioOrigemAndUsuarioDestino(bloqueador, bloqueado)
                .ifPresent(curtidaRepository::delete);
        curtidaRepository.findByUsuarioOrigemAndUsuarioDestino(bloqueado, bloqueador)
                .ifPresent(curtidaRepository::delete);

        Bloqueios novoBloqueio = new Bloqueios(bloqueador, bloqueado, request.motivo());
        Bloqueios salvo = bloqueioRepository.save(novoBloqueio);
        return bloqueioMapper.toResponse(salvo);
    }

    @Transactional
    public void desbloquearUsuario(Long idBloqueador, Long idBloqueado) {
        Usuario bloqueador = usuarioService.buscarEntidade(idBloqueador);
        Usuario bloqueado = usuarioService.buscarEntidade(idBloqueado);

        Bloqueios bloqueio = bloqueioRepository
                .findByUsuarioBloqueadorAndUsuarioBloqueado(bloqueador, bloqueado)
                .orElseThrow(() -> new RuntimeException("Bloqueio não encontrado!"));

        bloqueioRepository.delete(bloqueio);
    }

    public List<BloqueioResponse> listarBloqueados(Long idBloqueador) {
        Usuario bloqueador = usuarioService.buscarEntidade(idBloqueador);
        return bloqueioRepository.findByUsuarioBloqueador(bloqueador).stream()
                .map(bloqueioMapper::toResponse)
                .toList();
    }
}