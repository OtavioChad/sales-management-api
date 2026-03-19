package com.chad.sales.exception;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ProdutoNotFoundException.class)
    public ResponseEntity<String> handleProdutoNotFound(ProdutoNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                             .body(ex.getMessage());
    }

    @ExceptionHandler(ClienteNotFoundException.class)
    public ResponseEntity<String> handleClienteNotFound(ClienteNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                             .body(ex.getMessage());
    }

    @ExceptionHandler(VendaNotFoundException.class)
    public ResponseEntity<String> handleVendaNotFound(VendaNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                             .body(ex.getMessage());
    }

    @ExceptionHandler(UsuarioNotFoundException.class)
    public ResponseEntity<String> handleUsuarioNotFound(UsuarioNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                             .body(ex.getMessage());
    }

    @ExceptionHandler(EstoqueInsuficienteException.class)
    public ResponseEntity<String> handleEstoqueInsuficiente(EstoqueInsuficienteException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                             .body(ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenerico(Exception ex) {
        ex.printStackTrace(); // opcional
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                             .body("Ocorreu um erro inesperado: " + ex.getMessage());
    }
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationErrors(MethodArgumentNotValidException ex) {

        List<String> erros = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getDefaultMessage())
                .toList();

        return ResponseEntity.badRequest().body(erros);
    }
}