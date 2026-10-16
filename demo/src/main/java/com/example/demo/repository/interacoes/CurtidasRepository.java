package com.example.demo.repository.interacoes;

import com.example.demo.model.Usuario;
import com.example.demo.model.interacoes.Curtidas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CurtidasRepository extends JpaRepository<Curtidas, Long> {

    //  Verifica se já existe uma curtida do Usuário A para o Usuário B (Retorna true ou false)
    boolean existsByUsuarioOrigemAndUsuarioDestino(Usuario usuarioOrigem, Usuario usuarioDestino);

    //  Busca a curtida específica para conseguirmos deletar (descurtir)
    Optional<Curtidas> findByUsuarioOrigemAndUsuarioDestino(Usuario usuarioOrigem, Usuario usuarioDestino);

    //  Conta o total de curtidas que um usuário recebeu
    long countByUsuarioDestino(Usuario usuarioDestino);

    //  Lista todas as curtidas recebidas por um usuário
    List<Curtidas> findByUsuarioDestino(Usuario usuarioDestino);

    //  O Spring Data JPA lê os nomes desses métodos em inglês (Derived Queries) e gera o SQL automaticamente :)
}