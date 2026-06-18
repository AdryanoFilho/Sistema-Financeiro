package br.com.financeiro.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public record DadosListagemLancamento(
        Long codigo,
        String descricao,
        LocalDate dataVencimento,
        BigDecimal valor,
        TipoLancamento tipo,
        String nomeCategoria,
        String nomePessoa
) {
    public DadosListagemLancamento(Lancamento lancamento) {
        this(
                lancamento.getCodigo(),
                lancamento.getDescricao(),
                lancamento.getDataVencimento(),
                lancamento.getValor(),
                lancamento.getTipo(),
                
                lancamento.getCategoria().getNome(),
                lancamento.getPessoa().getNome()
        );
    }
}
