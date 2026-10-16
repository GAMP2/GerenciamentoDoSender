package com.example.demo.model;

import com.example.demo.model.enums.Cargo;
import com.example.demo.model.enums.Genero;
import com.example.demo.model.enums.Objetivo;
import com.example.demo.model.enums.StatusUsuario;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDate;

@Entity
//Nome Da tabela no banco de dados
@Data // todo Isso gera o getters e os Setters de tudo
@NoArgsConstructor
@Table(name = "Usuario")
public class Usuario {
    //Um usuário tem apenas um perfil
    @Id
    //Cria um valor aleatório
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "O nome não pode estar em branco")
    String nome;
    //@notblank: Garante que o campo seja vazio
    @NotBlank(message = "O e-mail é obrigatório")
    @Email(message = "O email deve ser válido")
    private String email;

    @Min(value = 0, message = "A idade não pode ser negativa")
    private int idade;

    //@NotNull: Garante que o campo não seja enviado como nulo.
    @NotNull(message = "A data de nascimento é obrigatótia")
    @Past(message = "A data de nascimento não pode ser uma data futura ")
    //@JsonFormat(pattern = "yyyy-MM-dd"): Garante que o Jackson consiga converter a String do JSON para a data em Java corretamente.
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dtNasc;

    @NotNull(message = "A data de criacao é obrigatória")
    @Past(message = "A data de criação não pode ser futura")
    private LocalDate dataCriacao;

    @NotBlank(message = "A senha é obrigatória")
    @Size(min = 8, max = 20, message = "A senha deve ter entre 8 e 20 caracteres")
    //@Pattern(regexp = "..."): Aplica uma Expressão Regular (Regex) para garantir a força da senha:
//(?=.*[0-9]): Exige pelo menos um número.
//
//(?=.*[a-z]): Exige pelo menos uma letra minúscula.
//
//(?=.*[A-Z]): Exige pelo menos uma letra maiúscula.
//
//(?=.*[@#$%^&+=!]): Exige pelo menos um símbolo/caractere especial.
    @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!]).*$",
            message = "A senha deve conter pelo menos uma letra maiúscula, uma minúscula, um número e um caractere especial"
    )
    private String senhaHash;

    private String biografia;

    public String telefone;

    @Min(value = 16, message = "A idade não pode ser menor que 16 anos!")
    private int preferenciaMin;

    private int preferenciaMax;

    private boolean verificado;

    private LocalDate dtAtualizacao;

    private LocalDate dtDeletar;

    private boolean ocultoFeed;

///////////////////////ENUMS////////////////////////////////////////////////////////////////////////////////
    //Renomeia o enum como palavra para o banco de dados
    @Enumerated(EnumType.STRING)
    private Objetivo objetivo;

    @Enumerated(EnumType.STRING)
    private Genero genero;

    @Enumerated(EnumType.STRING)
    private Cargo cargo;

    @Enumerated(EnumType.STRING)
    private StatusUsuario status;
    private String motivoBloqueio;
/////////////////////////////////////////////////////////////////////////////////////////////////







}
