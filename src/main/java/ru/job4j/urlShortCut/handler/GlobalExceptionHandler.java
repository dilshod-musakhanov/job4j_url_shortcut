package ru.job4j.urlshortcut.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.NoSuchElementException;

@ControllerAdvice
public class GlobalExceptionHandler {

    private final ObjectMapper objectMapper;

    public GlobalExceptionHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public void handleMethodArgumentNotValidException(
            Exception e, HttpServletRequest request, HttpServletResponse response
    ) throws IOException {
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setContentType("application/json");
        String customErrorMessage = "Input value is not valid";
        response.getWriter().write(objectMapper.writeValueAsString(new HashMap<>() {{
            put("message", customErrorMessage);
            put("type", e.getClass());
        }}));
    }

    @ExceptionHandler(value = {NoSuchElementException.class})
    public void handleNoSuchElementException(
            Exception e, HttpServletRequest request, HttpServletResponse response
    ) throws IOException {
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setContentType("application/json");
        response.getWriter().write(objectMapper.writeValueAsString(new HashMap<>() {{
            put("message", e.getMessage());
            put("type", e.getClass());
        }}));
    }

    @ExceptionHandler(value = {BadCredentialsException.class})
    public void handleBadCredentialsException(
            Exception e, HttpServletRequest request, HttpServletResponse response
    ) throws IOException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json");
        response.getWriter().write(objectMapper.writeValueAsString(new HashMap<>() {{
            put("message", e.getMessage());
            put("type", e.getClass());
        }}));
    }


}
