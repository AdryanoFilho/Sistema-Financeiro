package br.com.financeiro.controller;

import br.com.financeiro.model.*;
import br.com.financeiro.repository.CategoriaRepository;
import br.com.financeiro.repository.LancamentoRepository;
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
@RequestMapping("/lancamentos")
public class LancamentoController {

    
    
    
    
    @Autowired
    private LancamentoRepository lancamentoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private PessoaRepository pessoaRepository;

    
    
    
    
    
    
    
    
    
    
    
    
    
    @PostMapping
    @Transactional
    public ResponseEntity<DadosDetalhamentoLancamento> cadastrar(
            @RequestBody @Valid DadosCadastroLancamento dados,
            UriComponentsBuilder uriBuilder) {

        
        
        var categoria = categoriaRepository.getReferenceById(dados.categoria().codigo());

        
        var pessoa = pessoaRepository.getReferenceById(dados.pessoa().codigo());

        
        var lancamento = new Lancamento(dados, categoria, pessoa);
        lancamentoRepository.save(lancamento);

        var uri = uriBuilder.path("/lancamentos/{codigo}").buildAndExpand(lancamento.getCodigo()).toUri();

        
        return ResponseEntity.created(uri).body(new DadosDetalhamentoLancamento(lancamento));
    }

    
    
    
    
    
    
    @GetMapping
    public ResponseEntity<Page<DadosListagemLancamento>> listar(
            @PageableDefault(size = 10, sort = "dataVencimento") Pageable pageable) {

        
        
        var page = lancamentoRepository.findAll(pageable)
                .map(DadosListagemLancamento::new);
        return ResponseEntity.ok(page);
    }

    
    
    
    
    @GetMapping("/{codigo}")
    public ResponseEntity<DadosDetalhamentoLancamento> buscar(@PathVariable Long codigo) {
        return lancamentoRepository.findById(codigo)
                .map(l -> ResponseEntity.ok(new DadosDetalhamentoLancamento(l)))
                .orElse(ResponseEntity.notFound().build());
    }

    
    
    
    
    
    @PutMapping("/{codigo}")
    @Transactional
    public ResponseEntity<DadosDetalhamentoLancamento> atualizar(
            @PathVariable Long codigo,
            @RequestBody DadosAtualizacaoLancamento dados) {

        var lancamentoOptional = lancamentoRepository.findById(codigo);
        if (lancamentoOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        
        Categoria categoria = null;
        if (dados.categoria() != null) {
            categoria = categoriaRepository.getReferenceById(dados.categoria().codigo());
        }

        Pessoa pessoa = null;
        if (dados.pessoa() != null) {
            pessoa = pessoaRepository.getReferenceById(dados.pessoa().codigo());
        }

        var lancamento = lancamentoOptional.get();
        
        lancamento.atualizar(dados, categoria, pessoa);

        return ResponseEntity.ok(new DadosDetalhamentoLancamento(lancamento));
    }

    
    
    
    
    
    @DeleteMapping("/{codigo}")
    @Transactional
    public ResponseEntity<Void> excluir(@PathVariable Long codigo) {
        if (!lancamentoRepository.existsById(codigo)) {
            return ResponseEntity.notFound().build();
        }
        lancamentoRepository.deleteById(codigo);
        
        return ResponseEntity.noContent().build();
    }
}
