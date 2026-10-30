package com.example.demo.repository.interacoes;

import com.example.demo.model.Usuario;
import com.example.demo.model.interacoes.Conversa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConversaRepository extends JpaRepository<Conversa, Long> {

    // A conversa entre A e B é a mesma que entre B e A, por isso o OR
    @Query("""
            SELECT c FROM Conversa c
            WHERE (c.usuario1 = :a AND c.usuario2 = :b)
               OR (c.usuario1 = :b AND c.usuario2 = :a)
            """)
    Optional<Conversa> buscarEntreUsuarios(@Param("a") Usuario a, @Param("b") Usuario b);

    // Todas as conversas de um usuário, da mais recente para a mais antiga
    @Query("""
            SELECT c FROM Conversa c
            WHERE c.usuario1 = :usuario OR c.usuario2 = :usuario
            ORDER BY c.atualizadoEm DESC
            """)
    List<Conversa> listarDoUsuario(@Param("usuario") Usuario usuario);
}