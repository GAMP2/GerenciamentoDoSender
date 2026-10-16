package com.example.demo.service.interacoes;

import com.example.demo.model.Usuario;
import com.example.demo.model.interacoes.Curtidas;
import com.example.demo.repository.interacoes.CurtidasRepository;
import com.example.demo.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CurtidaService {
    @Autowired
    private CurtidasRepository curtidasRepository;

    @Autowired
    private UsuarioService usuarioService;

    // esse método tira a necessidade de rotas separadas de "curtir" e "descurtir" no front-end, o mesmo botão resolve ambas as ações.
    public String alternarCurtida(long idOrigem, long idDestino) {
        // Não pode curtir a si mesmo
        if (idOrigem == idDestino) {
            throw new RuntimeException("Um usuário não pode curtir a si mesmo!");
        }
        //Busca os dois usuários no banco via UsuarioService
        Usuario usuarioOrigem = usuarioService.buscarPorId(idOrigem);
        Usuario usuarioDestino = usuarioService.buscarPorId(idDestino);

        //Verifica se a curtida ja existe
        Optional<Curtidas> curtidasExistente = curtidasRepository.findByUsuarioOrigemAndUsuarioDestino(usuarioOrigem, usuarioDestino);

        if (curtidasExistente.isPresent()) {
            // Se já curtiu, descurte (Remove do banco)
            curtidasRepository.delete(curtidasExistente.get());
            return "Curtida removida com sucesso!";
        } else {
            // Se não curtiu ainda, salva nova curtida
            Curtidas novaCurtida = new Curtidas();
            curtidasRepository.save(novaCurtida);
            return "Usuário curtido com sucesso!";
        }
    }

    public long contarCurtidasRecebidas(long idDestino) {
        Usuario usuarioDestino = usuarioService.buscarPorId(idDestino);
        return curtidasRepository.countByUsuarioDestino(usuarioDestino);
    }

    // Lista todas as curtidas recebidas por um usuário
    public List<Curtidas> listarCurtidasRecebidas(long idDestino) {
        Usuario usuarioDestino = usuarioService.buscarPorId(idDestino);
        return curtidasRepository.findByUsuarioDestino(usuarioDestino);
    }
}
