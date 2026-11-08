package com.example.demo.model;

import org.springframework.data.annotation.Id;

public class FotosUsuario {
    @Id
    private long id;
    private long idUsuario;
    private String fotoUrl;
}
