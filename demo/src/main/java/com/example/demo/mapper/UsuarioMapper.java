package com.example.demo.mapper;

import com.example.demo.dto.request.UsuarioRequest;
import com.example.demo.dto.response.UsuarioResponse;
import com.example.demo.entity.Usuario;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Period;
//@Component diz ao Spring “crie um desses e guarde, porque o service vai pedir”.
// É por isso que o service o recebe pelo construtor e nunca faz new UsuarioMapper().
@Component
public class UsuarioMapper {
    //toEntity faz o caminho de ida: pega cada campo do Request e coloca no objeto Usuario
    // Request (o que o cliente enviou) -> Entity (o que vai para o banco)
    public Usuario toEntity(UsuarioRequest request) {
        Usuario usuario = new Usuario();
        usuario.setNome(request.nome());
        usuario.setUsername(request.username());
        usuario.setEmail(request.email());
        usuario.setTelefone(request.telefone());
        usuario.setDtNasc(request.dtNasc());
        usuario.setPapel(request.papel());
        usuario.setBiografia(request.biografia());
        usuario.setObjetivo(request.objetivo());
        usuario.setPreferenciaMin(request.preferenciaMin());
        usuario.setPreferenciaMax(request.preferenciaMax());
        return usuario;
    }

    // Entity (o que veio do banco) -> Response (o que a API devolve)
    public UsuarioResponse toResponse(Usuario usuario) {
        // a idade não existe no banco, então é calculada a partir da data de nascimento
        int idade = Period.between(usuario.getDtNasc(), LocalDate.now()).getYears();

        return new UsuarioResponse(
                usuario.getId(),
                usuario.getNome(),
                usuario.getUsername(),
                usuario.getEmail(),
                idade,
                usuario.getDtNasc(),
                usuario.getPapel(),
                usuario.getBiografia(),
                usuario.getObjetivo(),
                usuario.getPreferenciaMin(),
                usuario.getPreferenciaMax(),
                usuario.isVerificado(),
                usuario.getCriadoEm(),
                null
        );
    }
}