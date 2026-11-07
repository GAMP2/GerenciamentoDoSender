package com.example.demo.service;

import com.example.demo.dto.request.CurtidaRequest;
import com.example.demo.dto.response.CurtidaResponse;
import com.example.demo.dto.response.CurtidaStatusResponse;
import com.example.demo.entity.Conexao;
import com.example.demo.entity.enums.StatusConexao;
import com.example.demo.entity.enums.TipoConexao;
import com.example.demo.mapper.CurtidaMapper;
import com.example.demo.entity.Curtidas;
import com.example.demo.entity.Usuario;
import com.example.demo.repository.ConexaoRepository;
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
    private final ConexaoRepository conexaoRepository;


    public CurtidaService(CurtidasRepository curtidasRepository,
                          UsuarioService usuarioService,
                          CurtidaMapper curtidaMapper,
    ConexaoRepository conexaoRepository){
        this.curtidasRepository = curtidasRepository;
        this.usuarioService = usuarioService;
        this.curtidaMapper = curtidaMapper;
        this.conexaoRepository = conexaoRepository;
    }
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

        boolean possuiCurtidaInversa = curtidasRepository
                .findByUsuarioOrigemAndUsuarioDestino(destino, origem).isPresent();

        if (possuiCurtidaInversa) {
            Conexao novaConexao = new Conexao();

            // Regra para respeitar a constraint chk_conexoes_ordem_par do banco:
            // O menor UUID precisa ser sempre o Usuario A
            if (origem.getId().compareTo(destino.getId()) < 0) {
                novaConexao.setUsuarioA(origem);
                novaConexao.setUsuarioB(destino);
            } else {
                novaConexao.setUsuarioA(destino);
                novaConexao.setUsuarioB(origem);
            }

            novaConexao.setTipo(com.example.demo.entity.enums.TipoConexao.MATCH);
            novaConexao.setStatus(com.example.demo.entity.enums.StatusConexao.ATIVA);

            conexaoRepository.save(novaConexao);
        }
        return new CurtidaStatusResponse(true,"Match realizado com sucesso!");
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