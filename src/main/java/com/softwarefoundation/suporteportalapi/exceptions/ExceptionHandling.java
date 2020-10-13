package com.softwarefoundation.suporteportalapi.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionHandling {

    private static final String ACCOUNT_LOCKED = "Sua conta foi bloqueada. Contate o administrador";
    private static final String METHOD_IS_NOT_ALLOWED = "Método não permitido";
    private static final String INTERNAL_SERVER_ERROR_MSG = "Ocorreu um erro de processamento. Contate o administrador";
    private static final String INCORRECT_CREDENTIALS = "Usuário e/ou senha incorreta. Tente novamente";
    private static final String ACOUNT_DISABLED = "Sua conta foi desativada";
    private static final String ERROR_PROCESSING_FILE = "Erro durante o processamento do arquivo";
    private static final String NOT_ENOUGH_PERMISSION = "Você não tem permissão para acessar este recurso";

}
