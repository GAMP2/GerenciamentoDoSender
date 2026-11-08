package com.example.demo.dto.request;

import com.example.demo.model.enums.Genero;
import com.example.demo.model.enums.Objetivo;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record UsuarioRequest(

        @NotBlank(message = "O nome não pode estar em branco")
        String nome,

        @NotBlank(message = "O e-mail é obrigatório")
        @Email(message = "O email deve ser válido")
        String email,

        @NotNull(message = "A data de nascimento é obrigatória")
        @Past(message = "A data de nascimento não pode ser uma data futura")
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate dtNasc,

        @NotBlank(message = "A senha é obrigatória")
        @Size(min = 8, max = 20, message = "A senha deve ter entre 8 e 20 caracteres")
        @Pattern(
                regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!]).*$",
                message = "A senha deve conter pelo menos uma letra maiúscula, uma minúscula, um número e um caractere especial"
        )
        String senha,

        String biografia,

        String telefone,

        @Min(value = 16, message = "A preferência mínima de idade não pode ser menor que 16 anos")
        int preferenciaMin,

        @Min(value = 16, message = "A preferência máxima de idade não pode ser menor que 16 anos")
        int preferenciaMax,

        Objetivo objetivo,

        Genero genero
) {}