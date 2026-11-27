package com.example.demo.repository.interacoes;

import com.example.demo.model.Usuario;
import com.example.demo.model.interacoes.Conversa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ConversaRepository extends JpaRepository<Conversa, String> {

    @Query("""
        select c
        from Conversa c
        where (c.usuario1 = :u1 and c.usuario2 = :u2)
           or (c.usuario1 = :u2 and c.usuario2 = :u1)
    """)
    List<Conversa> buscarEntreUsuarios(@Param("u1") Usuario u1, @Param("u2") Usuario u2);
}