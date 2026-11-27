package com.example.demo.mapper;

import com.example.demo.dto.request.DenunciaRequest;
import com.example.demo.dto.response.DenunciaResponse;
import com.example.demo.entity.Denuncia;
import com.example.demo.entity.Usuario;
import org.springframework.stereotype.Component;
@Component
public class DenunciaMapper {

    public Denuncia toEntity(DenunciaRequest request, Usuario denunciante, Usuario denunciado) {
        if (request == null) return null;

        Denuncia entity = new Denuncia();
        entity.setDenunciante(denunciante);
        entity.setDenunciado(denunciado);
        entity.setMotivo(request.motivo());
        entity.setDetalhes(request.detalhes());

        return entity;
    }

    public DenunciaResponse toResponse(Denuncia entity) {
        if (entity == null) return null;

        return new DenunciaResponse(
                entity.getId(),
                entity.getDenunciante().getId(),
                entity.getDenunciante().getNome(),
                entity.getDenunciado().getId(),
                entity.getDenunciado().getNome(),
                entity.getMotivo(),
                entity.getDetalhes(),
                entity.getStatus(),
                entity.getCriadaEm()
        );
    }
}
