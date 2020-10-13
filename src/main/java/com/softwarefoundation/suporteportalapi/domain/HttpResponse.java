package com.softwarefoundation.suporteportalapi.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.http.HttpStatus;

import java.util.Date;
import java.util.TimeZone;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class HttpResponse {

    /**
     * 200,201,400,404,500
     */
    private int httpStatusCode;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy hh:mm:ss", timezone = "America/Cuiaba")
    private Date timestamp = new Date();
    private HttpStatus httpStatus;
    private String reason;
    private String message;

    public HttpResponse(int httpStatusCode, HttpStatus httpStatus, String reason, String message) {
        this.httpStatusCode = httpStatusCode;
        this.httpStatus = httpStatus;
        this.reason = reason;
        this.message = message;
    }
}
