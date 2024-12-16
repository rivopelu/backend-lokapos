package utils;

import com.lokapos.exception.BadRequestException;
import com.lokapos.exception.NotAuthorizedException;
import com.lokapos.exception.NotFoundException;
import com.lokapos.exception.SystemErrorException;
import com.lokapos.model.response.BaseResponse;
import com.lokapos.model.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Slf4j
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(BadRequestException.class)
    protected ResponseEntity<BaseResponse> handleBadRequestException(BadRequestException ex) {
        ex.printStackTrace();
        log.warn("Bad Request : {}", ex.getMessage());

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(createBaseResponseError(ex.getMessage(), HttpStatus.BAD_REQUEST));
    }


    @ExceptionHandler(NotFoundException.class)
    protected ResponseEntity<BaseResponse> handleNotFoundException(NotFoundException ex) {
        ex.printStackTrace();
        log.warn("Not Found : {}", ex.getMessage());

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(createBaseResponseError(ex.getMessage(), HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler(NotAuthorizedException.class)
    protected ResponseEntity<BaseResponse> handleNotAuthorizedException(NotAuthorizedException ex) {
        ex.printStackTrace();
        log.warn("Not Authorized : {}", ex.getMessage());

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(createBaseResponseError(ex.getMessage(), HttpStatus.UNAUTHORIZED));
    }

    @ExceptionHandler(SystemErrorException.class)
    protected ResponseEntity<BaseResponse> handleInternalServerError(SystemErrorException ex) {
        ex.printStackTrace();
        log.warn("System Error : {}", ex.getMessage());

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(createBaseResponseError(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR));
    }


    private BaseResponse createBaseResponseError(String message, HttpStatusCode code) {
        ErrorResponse res = ErrorResponse.builder()
                .message(message)
                .code(code.value())
                .build();
        return BaseResponse.builder()
                .success(false)
                .errors(res)
                .build();
    }
}
