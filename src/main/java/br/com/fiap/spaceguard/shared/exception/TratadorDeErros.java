package br.com.fiap.spaceguard.shared.exception;

import jakarta.validation.ValidationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class TratadorDeErros {

    @ExceptionHandler(RecursoNaoEncontradoException.class)
    public ResponseEntity<Void> tratarErro404() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(RegraNegocioException.class)
    public ResponseEntity<Map<String, String>> tratarErroRegraNegocio(RegraNegocioException exception) {
        return ResponseEntity.badRequest().body(Map.of(
                "erro", exception.getMessage()
        ));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<DadosErroValidacao>> tratarErro400(MethodArgumentNotValidException exception) {
        var erros = exception.getFieldErrors()
                .stream()
                .map(DadosErroValidacao::new)
                .toList();

        return ResponseEntity.badRequest().body(erros);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, String>> tratarErroIntegridadeBanco() {
        return ResponseEntity.badRequest().body(Map.of(
                "erro", "Operação não permitida por violar uma regra de integridade do banco de dados."
        ));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Map<String, String>> tratarErroCredenciaisInvalidas() {
        return ResponseEntity.status(401).body(Map.of(
                "erro", "Login ou senha inválidos."
        ));
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<Map<String, String>> tratarErroAutenticacao() {
        return ResponseEntity.status(401).body(Map.of(
                "erro", "Falha na autenticação."
        ));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Map<String, String>> tratarErroAcessoNegado() {
        return ResponseEntity.status(403).body(Map.of(
                "erro", "Acesso negado para este recurso."
        ));
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Map<String, String>> tratarErroValidacao(ValidationException exception) {
        return ResponseEntity.badRequest().body(Map.of(
                "erro", exception.getMessage()
        ));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> tratarErro500() {
        return ResponseEntity.internalServerError().body(Map.of(
                "erro", "Erro interno inesperado. Verifique os logs da aplicação."
        ));
    }

    private record DadosErroValidacao(String campo, String mensagem) {

        public DadosErroValidacao(FieldError erro) {
            this(erro.getField(), erro.getDefaultMessage());
        }
    }
}