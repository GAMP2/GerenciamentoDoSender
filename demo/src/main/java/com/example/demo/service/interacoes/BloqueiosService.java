package com.example.demo.service.interacoes;

import com.example.demo.model.Usuario;
import com.example.demo.model.interacoes.Bloqueios;
import com.example.demo.model.interacoes.Curtidas;
import com.example.demo.repository.interacoes.BloqueioRepository;
import com.example.demo.repository.interacoes.CurtidasRepository;
import com.example.demo.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class BloqueiosService {

        @Autowired
        private BloqueioRepository bloqueioRepository;

        @Autowired
        private CurtidasRepository curtidaRepository;

        @Autowired
        private UsuarioService usuarioService;

        // 1. Método para BLOQUEAR um utilizador
        public String bloquearUsuario(long idBloqueador, long idBloqueado, String motivo) {
            //Não pode bloquear a si mesmo
            if (idBloqueador ==idBloqueado) {
                throw new RuntimeException("Não podes bloquear o teu próprio perfil!");
            }

            // Busca os utilizadores no banco
            Usuario bloqueador = usuarioService.buscarPorId(idBloqueador);
            Usuario bloqueado = usuarioService.buscarPorId(idBloqueado);

            // verifica se já está bloqueado
            if (bloqueioRepository.existsByUsuarioBloqueadorAndUsuarioBloqueado(bloqueador, bloqueado)) {
                throw new RuntimeException("Este utilizador já se encontra bloqueado!");
            }

            // se houver curtida (de A para B ou de B para A), apagar
            Optional<Curtidas> curtida1 = curtidaRepository.findByUsuarioOrigemAndUsuarioDestino(bloqueador, bloqueado);
            curtida1.ifPresent(curtidaRepository::delete);

            Optional<Curtidas> curtida2 = curtidaRepository.findByUsuarioOrigemAndUsuarioDestino(bloqueado, bloqueador);
            curtida2.ifPresent(curtidaRepository::delete);

            // Salva o novo bloqueio
            Bloqueios novoBloqueio = new Bloqueios(bloqueador, bloqueado, motivo);
            bloqueioRepository.save(novoBloqueio);

            return "Utilizador bloqueado com sucesso!";
        }

        //  Método para DESBLOQUEAR um utilizador
        public String desbloquearUsuario(long idBloqueador, long idBloqueado) {
            Usuario bloqueador = usuarioService.buscarPorId(idBloqueador);
            Usuario bloqueado = usuarioService.buscarPorId(idBloqueado);

            Bloqueios bloqueio = bloqueioRepository.findByUsuarioBloqueadorAndUsuarioBloqueado(bloqueador, bloqueado)
                    .orElseThrow(() -> new RuntimeException("Bloqueio não encontrado!"));

            bloqueioRepository.delete(bloqueio);
            return "Utilizador desbloqueado com sucesso!";
        }

        //  Listar quem o utilizador bloqueou
        public List<Bloqueios> listarBloqueados(long idBloqueador) {
            Usuario bloqueador = usuarioService.buscarPorId(idBloqueador);
            return bloqueioRepository.findByUsuarioBloqueador(bloqueador);
        }
    }