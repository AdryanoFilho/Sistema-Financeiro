package br.com.financeiro.controller;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class TratadorDeErros {

    
    
    
    
    
    
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Void> tratarErro404() {
        
        return ResponseEntity.notFound().build();
    }

    
    
    
    
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<DadosErroValidacao>> tratarErro400(
            MethodArgumentNotValidException ex) {

        
        
        
        
        var erros = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DadosErroValidacao::new)
                .toList();

        
        return ResponseEntity.badRequest().body(erros);
    }

    
    
    
    
    
    public record DadosErroValidacao(String campo, String mensagem) {

        
        
        public DadosErroValidacao(FieldError fieldError) {
            this(fieldError.getField(), fieldError.getDefaultMessage());
        }
    }
}
