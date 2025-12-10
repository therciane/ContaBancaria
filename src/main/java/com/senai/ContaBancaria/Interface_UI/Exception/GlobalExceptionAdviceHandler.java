package com.senai.ContaBancaria.Interface_UI.Exception;

import com.senai.ContaBancaria.Domain.Exceptions.*;

import com.senai.ContaBancaria.Domain.Exceptions.UsuarioNaoEncontradoException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.naming.AuthenticationException;
import java.nio.file.AccessDeniedException;
import java.util.LinkedHashMap;
import java.util.Map;

import static com.senai.ContaBancaria.Interface_UI.Exception.ProblemDetailsUtils.buildProblem;

@RestControllerAdvice
public class GlobalExceptionAdviceHandler {

    /* ==================== EXCEÇÕES DE REGRA DE NEGÓCIO ==================== */

    @ExceptionHandler(ContaMesmoTipoException.class)
    public ProblemDetail handleContaMesmoTipo(ContaMesmoTipoException ex, HttpServletRequest req) {
        return buildProblem(
                HttpStatus.CONFLICT,
                "Conta duplicada",
                ex.getMessage(),
                req.getRequestURI()
        );
    }

    @ExceptionHandler(EntidadeNaoEncontradaException.class)
    public ProblemDetail handleEntidadeNaoEncontrada(EntidadeNaoEncontradaException ex, HttpServletRequest req) {
        return buildProblem(
                HttpStatus.NOT_FOUND,
                "Registro não encontrado",
                ex.getMessage(),
                req.getRequestURI()
        );
    }

    @ExceptionHandler(RendimentoInvalidoException.class)
    public ProblemDetail handleRendimentoInvalido(RendimentoInvalidoException ex, HttpServletRequest req) {
        return buildProblem(
                HttpStatus.BAD_REQUEST,
                "Rendimento aplicado indevidamente",
                ex.getMessage(),
                req.getRequestURI()
        );
    }

    @ExceptionHandler(SaldoInsuficienteException.class)
    public ProblemDetail handleSaldoInsuficiente(SaldoInsuficienteException ex, HttpServletRequest req) {
        return buildProblem(
                HttpStatus.BAD_REQUEST,
                "Saldo insuficiente",
                ex.getMessage(),
                req.getRequestURI()
        );
    }

    @ExceptionHandler(TipoDeContaInvalidaException.class)
    public ProblemDetail handleTipoDeContaInvalida(TipoDeContaInvalidaException ex, HttpServletRequest req) {
        return buildProblem(
                HttpStatus.UNPROCESSABLE_ENTITY,
                "Tipo de conta inválido",
                ex.getMessage(),
                req.getRequestURI()
        );
    }

    @ExceptionHandler(TransferenciaMesmaContaException.class)
    public ProblemDetail handleTransferenciaMesmaConta(TransferenciaMesmaContaException ex, HttpServletRequest req) {
        return buildProblem(
                HttpStatus.BAD_REQUEST,
                "Transferência inválida",
                ex.getMessage(),
                req.getRequestURI()
        );
    }

    @ExceptionHandler(ValoresNegativosException.class)
    public ProblemDetail handleValoresNegativos(ValoresNegativosException ex, HttpServletRequest req) {
        return buildProblem(
                HttpStatus.BAD_REQUEST,
                "Valor negativo informado",
                ex.getMessage(),
                req.getRequestURI()
        );
    }

    @ExceptionHandler(AutenticacaoIoTExpiradaException.class)
    public ProblemDetail handleAutenticacaoIoT(AutenticacaoIoTExpiradaException ex, HttpServletRequest req) {
        return buildProblem(
                HttpStatus.REQUEST_TIMEOUT,
                "Autenticação IoT expirada",
                ex.getMessage(),
                req.getRequestURI()
        );
    }

    /* ==================== EXCEÇÕES DE AUTENTICAÇÃO / SEGURANÇA ==================== */

    @ExceptionHandler(UsuarioNaoEncontradoException.class)
    public ProblemDetail handleUsuarioNaoEncontrado(UsuarioNaoEncontradoException ex, HttpServletRequest req) {
        return buildProblem(
                HttpStatus.UNAUTHORIZED,
                "Usuário não encontrado",
                ex.getMessage(),
                req.getRequestURI()
        );
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ProblemDetail handleBadCredentials(BadCredentialsException ex, HttpServletRequest req) {
        return buildProblem(
                HttpStatus.UNAUTHORIZED,
                "Credenciais inválidas",
                ex.getMessage(),
                req.getRequestURI()
        );
    }

    @ExceptionHandler(AuthenticationException.class)
    public ProblemDetail handleAuth(AuthenticationException ex, HttpServletRequest req) {
        return buildProblem(
                HttpStatus.UNAUTHORIZED,
                "Falha de autenticação",
                ex.getMessage(),
                req.getRequestURI()
        );
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ProblemDetail handleAccessDenied(AccessDeniedException ex, HttpServletRequest req) {
        return buildProblem(
                HttpStatus.FORBIDDEN,
                "Acesso negado",
                ex.getMessage(),
                req.getRequestURI()
        );
    }

    /* ==================== ERROS DE VALIDAÇÃO ==================== */

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleValidation(MethodArgumentNotValidException ex, HttpServletRequest req) {

        ProblemDetail problem = buildProblem(
                HttpStatus.BAD_REQUEST,
                "Erro de validação",
                "Um ou mais campos são inválidos",
                req.getRequestURI()
        );

        Map<String, String> errors = new LinkedHashMap<>();
        ex.getBindingResult()
                .getFieldErrors()
                .forEach(err -> errors.put(err.getField(), err.getDefaultMessage()));

        problem.setProperty("errors", errors);
        return problem;
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ProblemDetail handleConstraintViolation(ConstraintViolationException ex, HttpServletRequest req) {

        ProblemDetail problem = buildProblem(
                HttpStatus.BAD_REQUEST,
                "Parâmetros inválidos",
                "Um ou mais parâmetros não atendem aos requisitos",
                req.getRequestURI()
        );

        Map<String, String> errors = new LinkedHashMap<>();
        ex.getConstraintViolations().forEach(v ->
                errors.put(v.getPropertyPath().toString(), v.getMessage())
        );

        problem.setProperty("errors", errors);
        return problem;
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ProblemDetail handleTypeMismatch(MethodArgumentTypeMismatchException ex, HttpServletRequest req) {

        return buildProblem(
                HttpStatus.BAD_REQUEST,
                "Tipo de parâmetro inválido",
                String.format(
                        "O parâmetro '%s' deve ser do tipo '%s'. Valor recebido: '%s'",
                        ex.getName(),
                        ex.getRequiredType() != null ? ex.getRequiredType().getSimpleName() : "desconhecido",
                        ex.getValue()
                ),
                req.getRequestURI()
        );
    }

    @ExceptionHandler(ConversionFailedException.class)
    public ProblemDetail handleConversionFailed(ConversionFailedException ex, HttpServletRequest req) {
        return buildProblem(
                HttpStatus.BAD_REQUEST,
                "Falha na conversão",
                "Um parâmetro não pôde ser convertido para o tipo esperado.",
                req.getRequestURI()
        );
    }

    /* ==================== EXCEÇÃO GENÉRICA ==================== */

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleGeneric(Exception ex, HttpServletRequest req) {
        return buildProblem(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Erro interno",
                ex.getMessage(),
                req.getRequestURI()
        );
    }
}