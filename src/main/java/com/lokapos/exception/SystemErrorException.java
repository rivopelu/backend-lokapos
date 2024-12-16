package com.lokapos.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class SystemErrorException extends RuntimeException {
    public SystemErrorException(Exception e) {
        super("Terjadi Kesalahan pada sistem");
        e.printStackTrace();
    }
}
