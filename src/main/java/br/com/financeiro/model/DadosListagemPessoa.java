package br.com.financeiro.model;

public record DadosListagemPessoa(
        Long codigo,
        String nome,
        Boolean ativo
) {
    
    public DadosListagemPessoa(Pessoa pessoa) {
        this(pessoa.getCodigo(), pessoa.getNome(), pessoa.getAtivo());
    }
}
