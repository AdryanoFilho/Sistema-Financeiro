package br.com.financeiro.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

public record DadosCadastroLancamento(

        @NotBlank(message = "Descrição é obrigatória")
        String descricao,

        
        @NotNull(message = "Data de vencimento é obrigatória")
        LocalDate dataVencimento,

        
        LocalDate dataPagamento,

        @NotNull(message = "Valor é obrigatório")
        BigDecimal valor,

        
        String observacao,

        @NotNull(message = "Tipo é obrigatório")
        TipoLancamento tipo,

        
        
        @NotNull(message = "Categoria é obrigatória")
        CodigoCategoria categoria,

        @NotNull(message = "Pessoa é obrigatória")
        CodigoPessoa pessoa
) {}
