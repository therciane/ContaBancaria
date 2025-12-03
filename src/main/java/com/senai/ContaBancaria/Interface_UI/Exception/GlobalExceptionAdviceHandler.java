package com.senai.ContaBancaria.Interface_UI.Exception;

import com.senai.ContaBancaria.Domain.Exceptions.*;

import com.senai.ContaBancaria.Domain.Exceptions.UsuarioNaoEncontradoException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.naming.AuthenticationException;
import java.net.URI;
import java.nio.file.AccessDeniedException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static com.senai.ContaBancaria.Interface_UI.Exception.ProblemDetailsUtils.buildProblem;

@RestControllerAdvice
public class GlobalExceptionAdviceHandler {

    @ExceptionHandler(ContaMesmoTipoException.class)
    public ProblemDetail handleContaMesmoTipo(ContaMesmoTipoException ex, HttpServletRequest request) {
        return buildProblem(
                HttpStatus.CONFLICT,
                "Não é possivel criar uma conta do do tipo já existente",
                ex.getMessage(),
                request.getRequestURI()
        );
    }

    @ExceptionHandler(EntidadeNaoEncontradaException.class)
    public ProblemDetail handleEntidadeNaoEncontrada(EntidadeNaoEncontradaException ex, HttpServletRequest request) {
        return buildProblem(
                HttpStatus.NOT_FOUND,
                "Não foi possivel encontrar a entidade",
                ex.getMessage(),
                request.getRequestURI()
        );
    }

    @ExceptionHandler(RendimentoInvalidoException.class)
    public ProblemDetail handleRendimentoInvalido(RendimentoInvalidoException ex, HttpServletRequest request) {
        return buildProblem(
                HttpStatus.BAD_REQUEST,
                "O rendimento só é permitido para contas do tipo `Poupança`",
                ex.getMessage(),
                request.getRequestURI()
        );
    }

    @ExceptionHandler(SaldoInsuficienteException.class)
    public ProblemDetail handleSaldoInsuficiente(SaldoInsuficienteException ex, HttpServletRequest request) {
        return buildProblem(
                HttpStatus.BAD_REQUEST,
                "O seu saldo é insuficiente para está operação",
                ex.getMessage(),
                request.getRequestURI()
        );
    }

    @ExceptionHandler(TipoDeContaInvalidaException.class)
    public ProblemDetail handleTipoDeContaInvalida(TipoDeContaInvalidaException ex, HttpServletRequest request) {
        return buildProblem(
                HttpStatus.UNPROCESSABLE_ENTITY,
                "Está conta é invalida",
                ex.getMessage(),
                request.getRequestURI()
        );
    }

    @ExceptionHandler(TransferenciaMesmaContaException.class)
    public ProblemDetail handleTransferenciaParaMesmaConta(TransferenciaMesmaContaException ex, HttpServletRequest request) {
        return buildProblem(
                HttpStatus.BAD_REQUEST,
                "Não é possivel transferir para sua própia conta",
                ex.getMessage(),
                request.getRequestURI()
        );
    }

    //Ele trata a exceção ValoresNegativosException e retorna uma resposta HTTP com o status 400 (Bad Request) e a mensagem da exceção.
    @ExceptionHandler(ValoresNegativosException.class)
    public ProblemDetail handleValoresNegativos(ValoresNegativosException ex, HttpServletRequest request) {
        return buildProblem(
                HttpStatus.BAD_REQUEST,
                "Valores negativos não são permitidos.",
                ex.getMessage(),
                request.getRequestURI()
        );
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleGenericException(Exception ex, HttpServletRequest request) {
        return buildProblem(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Erro do própio servidor",
                ex.getMessage(),
                request.getRequestURI()
        );
    }

    //Pega erros de digitação ou semelhantes
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail badRequest(MethodArgumentNotValidException ex, HttpServletRequest request) {
        ProblemDetail problem = buildProblem(
                HttpStatus.BAD_REQUEST,
                "Erro de validação",
                "Um ou mais campos são inválidos",
                request.getRequestURI()
        );

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(error -> errors.put(
                                error.getField(),
                                error.getDefaultMessage()
                        )
                );

        problem.setProperty("errors", errors);
        return problem;
    }


    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ProblemDetail handleTypeMismatch(MethodArgumentTypeMismatchException ex, HttpServletRequest request) {
        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problem.setTitle("Tipo de parâmetro inválido");
        problem.setDetail(String.format(
                "O parâmetro '%s' deve ser do tipo '%s'. Valor recebido: '%s'",
                ex.getName(),
                ex.getRequiredType() != null ? ex.getRequiredType().getSimpleName() : "desconhecido",
                ex.getValue()
        ));
        problem.setInstance(URI.create(request.getRequestURI()));
        return problem;
    }

    @ExceptionHandler(ConversionFailedException.class)
    public ProblemDetail handleConversionFailed(ConversionFailedException ex, HttpServletRequest request) {
        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problem.setTitle("Falha de conversão de parâmetro");
        problem.setDetail("Um parâmetro não pôde ser convertido para o tipo esperado.");
        problem.setInstance(URI.create(request.getRequestURI()));
        problem.setProperty("error", ex.getMessage());
        return problem;
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ProblemDetail handleConstraintViolation(ConstraintViolationException ex, HttpServletRequest request) {
        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problem.setTitle("Erro de validação nos parâmetros");
        problem.setDetail("Um ou mais parâmetros são inválidos");
        problem.setInstance(URI.create(request.getRequestURI()));

        Map<String, String> errors = new LinkedHashMap<>();
        ex.getConstraintViolations().forEach(violation -> {
            String campo = violation.getPropertyPath().toString();
            String mensagem = violation.getMessage();
            errors.put(campo, mensagem);
        });
        problem.setProperty("errors", errors);
        return problem;
    }

    @ExceptionHandler(AuthenticationException.class)
    public ProblemDetail handleAuthenticationException(AuthenticationException ex, HttpServletRequest request) {
        return buildProblem(
                HttpStatus.UNAUTHORIZED,
                "Não autenticado",
                ex.getMessage(),
                request.getRequestURI()
        );
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ProblemDetail handleAccessDeniedException(AccessDeniedException ex, HttpServletRequest request) {
        return buildProblem(
                HttpStatus.FORBIDDEN,
                "Acesso negado",
                ex.getMessage(),
                request.getRequestURI()
        );
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ProblemDetail handleBadCredentials(BadCredentialsException ex, HttpServletRequest request) {
        return buildProblem(
                HttpStatus.UNAUTHORIZED,
                "Credenciais inválidas",
                ex.getMessage(),
                request.getRequestURI()
        );
    }
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ProblemDetail handleMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpServletRequest request) {
        return buildProblem(
                HttpStatus.METHOD_NOT_ALLOWED,
                "Método não permitido",
                String.format("O método %s não é suportado para esta rota. Métodos suportados: %s",
                        ex.getMethod(),
                        String.join(", ", ex.getSupportedMethods() != null ? ex.getSupportedMethods() : new String[]{})),
                request.getRequestURI()
        );
    }

    @ExceptionHandler(UsuarioNaoEncontradoException.class)
    public ProblemDetail handleUsuarioNaoEncontrado(UsuarioNaoEncontradoException ex, HttpServletRequest request) {
        return buildProblem(
                HttpStatus.UNAUTHORIZED,
                "Usuário não encontrado",
                ex.getMessage(),
                request.getRequestURI()
        );
    }

    @ExceptionHandler(AutenticacaoIoTExpiradaException.class)
    public ProblemDetail handleAutenticacaoIotExpirada(AutenticacaoIoTExpiradaException ex, HttpServletRequest request) {
        return buildProblem(
                HttpStatus.GATEWAY_TIMEOUT,
                "Tempo expirado para autenticação IoT",
                ex.getMessage(),
                request.getRequestURI()
        );
    }

}