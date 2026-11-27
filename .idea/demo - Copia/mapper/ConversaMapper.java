package com.example.demo.mapper;

import com.example.demo.dto.response.ConversaResponse;
import com.example.demo.model.interacoes.Conversa;
import org.springframework.stereotype.Component;

@Component
public class ConversaMapper {

    public ConversaResponse toResponse(Conversa conversa) {
        return new ConversaResponse(
                conversa.getId(),
                conversa.getUsuario1().getId(),
                conversa.getUsuario1().getNome(),
                conversa.getUsuario2().getId(),
                conversa.getUsuario2().getNome(),
                conversa.getCriadoEm(),
                conversa.getAtualizadoEm()
        );
    }
}