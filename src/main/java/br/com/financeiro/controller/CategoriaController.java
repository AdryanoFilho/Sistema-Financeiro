package br.com.financeiro.controller;

import br.com.financeiro.model.Categoria;
import br.com.financeiro.model.DadosCadastroCategoria;
import br.com.financeiro.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController

@RequestMapping("/categorias")
public class CategoriaController {

    
    
    
    @Autowired
    private CategoriaRepository repository;

    
    
    
    
    
    @PostMapping
    public ResponseEntity<Categoria> cadastrar(
            @RequestBody DadosCadastroCategoria dados,
            UriComponentsBuilder uriBuilder) {

        
        var categoria = new Categoria(dados.nome());

        
        repository.save(categoria);

        
        
        var uri = uriBuilder.path("/categorias/{codigo}").buildAndExpand(categoria.getCodigo()).toUri();
        return ResponseEntity.created(uri).body(categoria);
    }

    
    
    
    
    
    
    
    @GetMapping
    public ResponseEntity<Page<Categoria>> listar(
            @PageableDefault(size = 10, sort = "nome") Pageable pageable) {

        
        var page = repository.findAll(pageable);
        return ResponseEntity.ok(page);
    }

    
    
    
    
    
    @GetMapping("/{codigo}")
    public ResponseEntity<Categoria> buscar(@PathVariable Long codigo) {
        
        
        return repository.findById(codigo)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    
    
    
    
    @DeleteMapping("/{codigo}")
    public ResponseEntity<Void> excluir(@PathVariable Long codigo) {
        if (!repository.existsById(codigo)) {
            return ResponseEntity.notFound().build();
        }
        repository.deleteById(codigo);
        
        return ResponseEntity.noContent().build();
    }
}
