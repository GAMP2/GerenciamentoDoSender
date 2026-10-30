package com.example.demo.mapper;

import com.example.demo.dto.response.MensagemResponse;
import com.example.demo.entity.Mensagem;
import org.springframework.stereotype.Component;

@Component
public class MensagemMapper {

    public MensagemResponse toResponse(Mensagem mensagem) {
        return new MensagemResponse(
                mensagem.getId(),
                mensagem.getConversa().getId(),
                mensagem.getRemetente().getId(),
                mensagem.getRemetente().getNome(),
                mensagem.getConteudo(),
                mensagem.getUrlImagem(),
                mensagem.getEnviadoEm(),
                mensagem.getLidoEm()
        );
    }
}