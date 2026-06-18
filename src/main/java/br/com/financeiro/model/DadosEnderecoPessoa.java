package br.com.financeiro.model;

public record DadosEnderecoPessoa(
        String logradouro,
        String numero,
        String complemento, 
        String bairro,
        String cep,
        String cidade,
        String estado
) {}
