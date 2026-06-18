package br.com.financeiro.controller;

import br.com.financeiro.model.*;
import br.com.financeiro.repository.PessoaRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/pessoas")
public class PessoaController {

    @Autowired
    private PessoaRepository repository;

    
    
    
    
    
    
    
    @PostMapping
    @Transactional
    public ResponseEntity<DadosDetalhamentoPessoa> cadastrar(
            @RequestBody @Valid DadosCadastroPessoa dados,
            UriComponentsBuilder uriBuilder) {

        
        var pessoa = new Pessoa(dados);
        repository.save(pessoa);

        
        var uri = uriBuilder.path("/pessoas/{codigo}").buildAndExpand(pessoa.getCodigo()).toUri();

        
        return ResponseEntity.created(uri).body(new DadosDetalhamentoPessoa(pessoa));
    }

    
    
    
    
    @GetMapping
    public ResponseEntity<Page<DadosListagemPessoa>> listar(
            @PageableDefault(size = 10, sort = "nome") Pageable pageable) {

        
        
        var page = repository.findAllByAtivoTrue(pageable)
                
                .map(DadosListagemPessoa::new);
        return ResponseEntity.ok(page);
    }

    
    
    
    @GetMapping("/{codigo}")
    public ResponseEntity<DadosDetalhamentoPessoa> buscar(@PathVariable Long codigo) {
        return repository.findById(codigo)
                .map(p -> ResponseEntity.ok(new DadosDetalhamentoPessoa(p)))
                .orElse(ResponseEntity.notFound().build());
    }

    
    
    
    
    
    
    @PutMapping("/{codigo}")
    @Transactional
    public ResponseEntity<DadosDetalhamentoPessoa> atualizar(
            @PathVariable Long codigo,
            @RequestBody DadosAtualizacaoPessoa dados) {

        var pessoaOptional = repository.findById(codigo);
        if (pessoaOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var pessoa = pessoaOptional.get();
        
        
        pessoa.atualizar(dados);

        return ResponseEntity.ok(new DadosDetalhamentoPessoa(pessoa));
    }

    
    
    
    
    
    @DeleteMapping("/{codigo}")
    @Transactional
    public ResponseEntity<Void> inativar(@PathVariable Long codigo) {
        var pessoaOptional = repository.findById(codigo);
        if (pessoaOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        
        pessoaOptional.get().inativar();

        
        return ResponseEntity.noContent().build();
    }

    
    
    
    @PutMapping("/{codigo}/ativo")
    @Transactional
    public ResponseEntity<Void> ativar(@PathVariable Long codigo) {
        var pessoaOptional = repository.findById(codigo);
        if (pessoaOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        pessoaOptional.get().ativar();
        return ResponseEntity.noContent().build();
    }
}
