package com.softwarefoundation.suporteportalapi.exceptions;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.softwarefoundation.suporteportalapi.domain.HttpResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.persistence.NoResultException;
import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.Objects;

@Slf4j
@RestControllerAdvice
public class ExceptionHandling {

    private static final String ACCOUNT_LOCKED_MSG = "Sua conta foi bloqueada. Contate o administrador";
    private static final String METHOD_IS_NOT_ALLOWED_MSG = "Método não permitido. Envie '%s' request";
    private static final String INTERNAL_SERVER_ERROR_MSG = "Ocorreu um erro de processamento. Contate o administrador";
    private static final String INCORRECT_CREDENTIALS_MSG = "Usuário e/ou senha incorreta. Tente novamente";
    private static final String ACOUNT_DISABLED_MSG = "Sua conta foi desativada";
    private static final String ERROR_PROCESSING_FILE_MSG = "Erro durante o processamento do arquivo";
    private static final String NOT_ENOUGH_PERMISSION_MSG = "Você não tem permissão para acessar este recurso";
    private static final String PAGE_NOT_FOUND_MSG = "Página não encontrada";

    @ExceptionHandler(DisabledException.class)
    private ResponseEntity<HttpResponse> accountDisabledException(){
        return createHttpResponse(HttpStatus.BAD_REQUEST, ACOUNT_DISABLED_MSG);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<HttpResponse> badCredentialsException(BadCredentialsException exception){
        log.error(exception.getMessage());
        return createHttpResponse(HttpStatus.BAD_REQUEST, INCORRECT_CREDENTIALS_MSG);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<HttpResponse> accessDeniedException(AccessDeniedException exception){
        log.error(exception.getMessage());
        return createHttpResponse(HttpStatus.FORBIDDEN, NOT_ENOUGH_PERMISSION_MSG);
    }

    @ExceptionHandler(LockedException.class)
    public ResponseEntity<HttpResponse> lockedException(LockedException exception){
        log.error(exception.getMessage());
        return createHttpResponse(HttpStatus.UNAUTHORIZED, ACCOUNT_LOCKED_MSG);
    }

    @ExceptionHandler(TokenExpiredException.class)
    public ResponseEntity<HttpResponse> tokenExpiredException(TokenExpiredException exception){
        log.error(exception.getMessage());
        return createHttpResponse(HttpStatus.UNAUTHORIZED, exception.getMessage());
    }

    @ExceptionHandler(EmailExistException.class)
    public ResponseEntity<HttpResponse> emailNotFoundException(EmailExistException exception){
        log.error(exception.getMessage());
        return createHttpResponse(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(UsernameExistException.class)
    public ResponseEntity<HttpResponse> userNameExistException(UsernameExistException exception){
        log.error(exception.getMessage());
        return createHttpResponse(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(EmailNotFoundException.class)
    public ResponseEntity<HttpResponse> emailNotFoundException(EmailNotFoundException exception){
        log.error(exception.getMessage());
        return createHttpResponse(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<HttpResponse> userNotFoundException(UserNotFoundException exception){
        log.error(exception.getMessage());
        return createHttpResponse(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<HttpResponse> noHandlerFoundException(NoHandlerFoundException exception){
        log.error(exception.getMessage());
        return createHttpResponse(HttpStatus.BAD_REQUEST, PAGE_NOT_FOUND_MSG);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<HttpResponse> methodNotSupportedException(HttpRequestMethodNotSupportedException exception){
        HttpMethod supportedMethod = Objects.requireNonNull(exception.getSupportedHttpMethods()).iterator().next();
        return createHttpResponse(HttpStatus.METHOD_NOT_ALLOWED, String.format(METHOD_IS_NOT_ALLOWED_MSG, supportedMethod));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<HttpResponse> internalServerException(Exception exception){
        log.error(exception.getMessage());
        return createHttpResponse(HttpStatus.INTERNAL_SERVER_ERROR, INTERNAL_SERVER_ERROR_MSG);
    }

    @ExceptionHandler(NoResultException.class)
    public ResponseEntity<HttpResponse> notFoundException(NoResultException exception){
        log.error(exception.getMessage());
        return createHttpResponse(HttpStatus.NOT_EXTENDED, exception.getMessage());
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<HttpResponse> notFoundException(IOException exception){
        log.error(exception.getMessage());
        return createHttpResponse(HttpStatus.INTERNAL_SERVER_ERROR, ERROR_PROCESSING_FILE_MSG);
    }


    private ResponseEntity<HttpResponse> createHttpResponse(HttpStatus httpStatus, String message){
        HttpResponse httpResponse = new HttpResponse(httpStatus.value(),
                httpStatus,
                httpStatus.getReasonPhrase().toUpperCase(),
                message.toUpperCase());

        return new ResponseEntity<>(httpResponse,httpStatus);
    }


}
