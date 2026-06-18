package br.com.financeiro.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "pessoa")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "codigo")
public class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigo;

    
    private String nome;

    
    
    @Embedded
    private Endereco endereco;

    
    
    
    private Boolean ativo;

    
    
    public Pessoa(DadosCadastroPessoa dados) {
        this.nome = dados.nome();
        
        this.endereco = new Endereco(
                dados.endereco().logradouro(),
                dados.endereco().numero(),
                dados.endereco().complemento(),
                dados.endereco().bairro(),
                dados.endereco().cep(),
                dados.endereco().cidade(),
                dados.endereco().estado()
        );
        
        this.ativo = true;
    }

    
    
    public void atualizar(DadosAtualizacaoPessoa dados) {
        if (dados.nome() != null) {
            this.nome = dados.nome();
        }
        if (dados.endereco() != null) {
            this.endereco = new Endereco(
                    dados.endereco().logradouro(),
                    dados.endereco().numero(),
                    dados.endereco().complemento(),
                    dados.endereco().bairro(),
                    dados.endereco().cep(),
                    dados.endereco().cidade(),
                    dados.endereco().estado()
            );
        }
    }

    
    
    public void inativar() {
        this.ativo = false;
    }

    
    public void ativar() {
        this.ativo = true;
    }
}
