    package com.example.demo.model;

    import com.example.demo.model.enums.Papel;
    import com.example.demo.model.enums.Genero;
    import com.example.demo.model.enums.Objetivo;
    import com.example.demo.model.enums.StatusUsuario;
    import jakarta.persistence.*;
    import lombok.Getter;
    import lombok.NoArgsConstructor;
    import lombok.Setter;


    import java.time.LocalDate;

    @Entity
    //Nome Da tabela no banco de dados
    @Getter
    @Setter
    @NoArgsConstructor
    @Table(name = "Usuario")
    public class Usuario {
        @Id
        //Cria um valor aleatório
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        String nome;
        private String email;
        private int idade;
        private LocalDate dtNasc;
        private LocalDate dataCriacao;

        private String senhaHash;

        private String biografia;

        public String telefone;

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
        private Papel cargo;

        @Enumerated(EnumType.STRING)
        private StatusUsuario status;
        private String motivoBloqueio;
    /////////////////////////////////////////////////////////////////////////////////////////////////







    }
