package br.com.financeiro.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DadosCadastroPessoa(

        
        @NotBlank(message = "Nome é obrigatório")
        String nome,

        
        
        @NotNull
        @Valid
        DadosEnderecoPessoa endereco
) {}
