package br.com.financeiro.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity

@Table(name = "categoria")

@Getter

@NoArgsConstructor

@AllArgsConstructor

@EqualsAndHashCode(of = "codigo")
public class Categoria {

    
    @Id

    
    
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigo;

    
    private String nome;

    
    
    public Categoria(String nome) {
        this.nome = nome;
    }
}
