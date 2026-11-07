package com.example.demo.entity;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "fotos_usuario")
public class FotosUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Column(name = "foto_url", nullable = false)
    private String fotoUrl;

    public FotosUsuario() {
    }

    public FotosUsuario(Usuario usuario, String fotoUrl) {
        this.usuario = usuario;
        this.fotoUrl = fotoUrl;
    }

    public FotosUsuario(UUID id, Usuario usuario, String fotoUrl) {
        this.id = id;
        this.usuario = usuario;
        this.fotoUrl = fotoUrl;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getFotoUrl() {
        return fotoUrl;
    }

    public void setFotoUrl(String fotoUrl) {
        this.fotoUrl = fotoUrl;
    }
}