package com.example.demo.repository;

import com.example.demo.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.Optional;

//Ao dar o extends JpaRepository,
// o Spring já te dá de graça dezenas de métodos sem você ter digitado uma linha de SQL
//interface porque ele escreve todo o código de banco de dados por você em tempo de execução
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario , Long> {

    //esses nomes não são de variaveis, e sim de instruções que o programa fornece
    boolean existsByEmail(String email);

    Optional<Usuario> findByEmail(String email);
}
