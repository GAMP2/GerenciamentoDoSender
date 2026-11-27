package com.example.demo.mapper;

import com.example.demo.dto.response.ConversaResponse;
import com.example.demo.entity.Conversa;
import org.springframework.stereotype.Component;

@Component
public class ConversaMapper {

    public ConversaResponse toResponse(Conversa conversa) {
        var conexao = conversa.getConexao();
        return new ConversaResponse(
                conversa.getId(),
                conexao.getId(),
                conexao.getUsuarioA().getId(),
                conexao.getUsuarioA().getNome(),
                conexao.getUsuarioB().getId(),
                conexao.getUsuarioB().getNome(),
                conversa.getCriadoEm()
        );
    }
}