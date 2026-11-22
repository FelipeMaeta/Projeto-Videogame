package br.senac.tads.dsw.gerenciador_jogos;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class TratadorDeErros {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroDto> tratarValidacao(MethodArgumentNotValidException ex) {
        List<String> erros = ex.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .distinct()
                .toList();
        return ResponseEntity.badRequest().body(new ErroDto("Existem campos inválidos.", erros));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErroDto> tratarCorpoInvalido(HttpMessageNotReadableException ex) {
        return ResponseEntity.badRequest().body(new ErroDto(
                "Dados inválidos: verifique se os campos numéricos foram preenchidos corretamente.",
                List.of()));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErroDto> tratarParametroInvalido(MethodArgumentTypeMismatchException ex) {
        return ResponseEntity.badRequest().body(new ErroDto("Identificador inválido.", List.of()));
    }

    @ExceptionHandler(RegraNegocioException.class)
    public ResponseEntity<ErroDto> tratarRegraNegocio(RegraNegocioException ex) {
        return ResponseEntity.badRequest().body(new ErroDto(ex.getMessage(), List.of()));
    }

    @ExceptionHandler(NaoEncontradoException.class)
    public ResponseEntity<ErroDto> tratarNaoEncontrado(NaoEncontradoException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErroDto(ex.getMessage(), List.of()));
    }
}
