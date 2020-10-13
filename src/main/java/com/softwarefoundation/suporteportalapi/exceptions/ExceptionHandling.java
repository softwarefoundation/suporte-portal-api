package com.softwarefoundation.suporteportalapi.exceptions;

import com.softwarefoundation.suporteportalapi.domain.HttpResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.DisabledException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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

    @ExceptionHandler(DisabledException.class)
    private ResponseEntity<HttpResponse> accountDisabledException(){
        return createHttpResponse(HttpStatus.BAD_REQUEST, ACOUNT_DISABLED_MSG);
    }


    private ResponseEntity<HttpResponse> createHttpResponse(HttpStatus httpStatus, String message){
        HttpResponse httpResponse = new HttpResponse(httpStatus.value(),
                httpStatus,
                httpStatus.getReasonPhrase().toUpperCase(),
                message.toUpperCase());

        return new ResponseEntity<>(httpResponse,httpStatus);
    }


}
