package com.majesticbyte.events;

import com.majesticbyte.exceptions.AppConstraintException;
import com.majesticbyte.exceptions.ExceptionMessage;
import org.springframework.data.rest.webmvc.RepositoryRestExceptionHandler;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.logging.Logger;

@ControllerAdvice(basePackageClasses = RepositoryRestExceptionHandler.class)
public class GlobalControllerExceptionHandler {

    Logger logger = Logger.getLogger("GlobalControllerExceptionHandler");


    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(AppConstraintException.class)
    @ResponseBody
    public ExceptionMessage badRequest(HttpServletRequest req, AppConstraintException exception) {
        ExceptionMessage response = new ExceptionMessage(exception.getLocalizedMessage());
        return response;
    }
}