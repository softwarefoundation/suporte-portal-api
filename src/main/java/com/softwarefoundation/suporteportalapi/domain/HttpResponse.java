package com.softwarefoundation.suporteportalapi.domain;

import lombok.*;
import org.springframework.http.HttpStatus;

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
    private HttpStatus httpStatus;
    private String reason;
    private String message;


}
