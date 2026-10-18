package com.example.demo.mapper;

import com.example.demo.dto.request.UsuarioRequest;
import com.example.demo.dto.response.UsuarioResponse;
import com.example.demo.model.Usuario;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {

    public Usuario toEntity(UsuarioRequest request) {
        Usuario usuario = new Usuario();
        usuario.setNome(request.nome());
        usuario.setEmail(request.email());
        usuario.setDtNasc(request.dtNasc());
        usuario.setBiografia(request.biografia());
        usuario.setTelefone(request.telefone());
        usuario.setPreferenciaMin(request.preferenciaMin());
        usuario.setPreferenciaMax(request.preferenciaMax());
        usuario.setObjetivo(request.objetivo());
        usuario.setGenero(request.genero());
        return usuario;
    }

    public UsuarioResponse toResponse(Usuario usuario) {
        return new UsuarioResponse(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getIdade(),
                usuario.getDtNasc(),
                usuario.getBiografia(),
                usuario.getPreferenciaMin(),
                usuario.getPreferenciaMax(),
                usuario.isVerificado(),
                usuario.getObjetivo(),
                usuario.getGenero(),
                usuario.getDataCriacao()
        );
    }
}