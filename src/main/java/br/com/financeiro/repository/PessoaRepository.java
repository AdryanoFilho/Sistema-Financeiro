package br.com.financeiro.repository;

import br.com.financeiro.model.Pessoa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

    
    
    
    
    
    
    
    Page<Pessoa> findAllByAtivoTrue(Pageable pageable);
}
