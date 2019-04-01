package io.dynabiz.web.error;


import io.dynabiz.BusinessException;
import io.dynabiz.web.response.GeneralResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import javax.websocket.DecodeException;
import java.rmi.ServerException;

@RestControllerAdvice
public class ErrorHandlerController {

    private static Logger logger = LoggerFactory.getLogger(ErrorHandlerController.class);

    public ErrorHandlerController() {
        logger.info("Global controller error handler enabled.");
    }

    @ExceptionHandler(value = { BusinessException.class })
    @ResponseStatus(HttpStatus.OK)
    public GeneralResponse serverException(BusinessException ex, WebRequest req) {
        logger.warn("[BusinessException] Handled business exception", ex);
        return new GeneralResponse(ex);
    }

    @ExceptionHandler(value = { DecodeException.class })
    @ResponseStatus(HttpStatus.OK)
    public GeneralResponse serverException(DecodeException ex, WebRequest req) {
        logger.warn("[DecodeException] Handled decode exception", ex);
        if(ex.getCause() instanceof  ServerException)
            return new GeneralResponse((ServerException) ex.getCause());
        else
            return new GeneralResponse((Exception) ex.getCause());
    }

    @ExceptionHandler(value = { Exception.class })
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public GeneralResponse serverException(Exception ex, WebRequest req) {
        logger.warn("[GeneralResponse] Handled exception", ex);
        return new GeneralResponse(ex);
    }



}
