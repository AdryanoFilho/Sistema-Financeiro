package br.com.financeiro.model;

public record DadosDetalhamentoPessoa(
        Long codigo,
        String nome,
        Endereco endereco,
        Boolean ativo
) {
    
    
    public DadosDetalhamentoPessoa(Pessoa pessoa) {
        this(
                pessoa.getCodigo(),
                pessoa.getNome(),
                pessoa.getEndereco(),
                pessoa.getAtivo()
        );
    }
}
