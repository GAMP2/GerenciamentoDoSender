package com.example.demo.repository;

import com.example.demo.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

//Ao dar o extends JpaRepository,
// o Spring já te dá de graça dezenas de métodos sem você ter digitado uma linha de SQL
//interface porque ele escreve todo o código de banco de dados por você em tempo de execução
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
    Optional<Usuario> findByEmail(String email);
    List<Usuario> findByDeletadoEmIsNull();
}
