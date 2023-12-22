package com.hendisantika.abcbank.exception;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.hendisantika.abcbank.util.AppUtil;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * Project : ABC-BANK
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 12/22/23
 * Time: 09:16
 * To change this template use File | Settings | File Templates.
 */
@ControllerAdvice(basePackages = {"com.abcbank.accountmaintenance"})
public class GlobalResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {

        logger.error(ExceptionUtils.getStackTrace(ex));

        Map<String, ObjectNode> messageMap = new LinkedHashMap<>();
        //Get all errors
        ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .forEach(x -> messageMap.put(x.getField(), createObjNode(x.getField(), x.getDefaultMessage())));

        ObjectNode errorJsonNode = AppUtil.createErrorJsonNode(status
                , ((ServletWebRequest) request).getRequest().getRequestURI()
                , messageMap);

        return new ResponseEntity<>(errorJsonNode, headers, status);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<Object> handleEntityNotFound(HttpServletRequest request, EntityNotFoundException ex) {
        String message = ex.getMessage();
        ObjectNode errorJsonNode = AppUtil.createErrorJsonNode(HttpStatus.NOT_FOUND,
                request.getRequestURI(), message);

        return new ResponseEntity<>(errorJsonNode, HttpStatus.NOT_FOUND);
    }

    private static ObjectNode createObjNode(String filed, String message) {
        ObjectNode jsonNode = AppUtil.createJsonNode();
        jsonNode.put("field", filed);
        jsonNode.put("message", message);
        return jsonNode;
    }
}
