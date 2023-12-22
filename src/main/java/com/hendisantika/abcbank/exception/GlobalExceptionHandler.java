package com.hendisantika.abcbank.exception;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.hendisantika.abcbank.util.AppUtil;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ValidationException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;

import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * Project : ABC-BANK
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 12/22/23
 * Time: 09:15
 * To change this template use File | Settings | File Templates.
 */
@ControllerAdvice(basePackages = {"com.hendisantika.abcbank"})
public class GlobalExceptionHandler extends ExceptionHandlerExceptionResolver {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler({ConstraintViolationException.class,
            ValidationException.class,
            org.hibernate.exception.ConstraintViolationException.class,
            org.springframework.dao.DataIntegrityViolationException.class})
    public void constraintViolationException(HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.CONFLICT.value());
    }

    @ExceptionHandler({org.springframework.web.HttpMediaTypeNotSupportedException.class,
            HttpMessageNotReadableException.class})
    public ResponseEntity<?> handleControllerException(HttpMediaTypeNotSupportedException ex, WebRequest req) {

        String message = ExceptionUtils.getRootCauseMessage(ex);
        ObjectNode errorJsonNode = AppUtil.createErrorJsonNode(HttpStatus.UNSUPPORTED_MEDIA_TYPE,
                ((ServletWebRequest) req).getRequest().getRequestURI(), message);
        logger.error(ExceptionUtils.getStackTrace(ex));
        return new ResponseEntity<>(errorJsonNode, HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }
}
