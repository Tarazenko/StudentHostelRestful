package by.bntu.tarazenko.hostelrestful.controllers;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import io.jsonwebtoken.MalformedJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.SQLException;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import by.bntu.tarazenko.hostelrestful.models.dtos.ErrorDTO;
import by.bntu.tarazenko.hostelrestful.services.exceptions.*;

@Slf4j
@ControllerAdvice
public class GlobalControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<Object> handleMaxSizeException(MaxUploadSizeExceededException ex) {
        log.warn("Max size exception stack: {} ", Arrays.toString(ex.getStackTrace()));
        ErrorDTO errorDTO = new ErrorDTO(HttpStatus.EXPECTATION_FAILED, ex.getClass().getName(), "File too large!");
        return new ResponseEntity<>(errorDTO, new HttpHeaders(), errorDTO.getHttpStatus());
    }

    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<Object> handleEntityNotFound(EntityNotFoundException ex) {
        log.warn("Not found exception stack: {} ", Arrays.toString(ex.getStackTrace()));
        ErrorDTO errorDTO = new ErrorDTO(HttpStatus.NOT_FOUND, ex.getClass().getName(), ex.getMessage());
        return new ResponseEntity<>(errorDTO, new HttpHeaders(), errorDTO.getHttpStatus());
    }

    @ExceptionHandler(EntityExistException.class)
    protected ResponseEntity<Object> handleEntityExist(EntityExistException ex) {
        log.warn("Entity already exist exception stack: {} ", Arrays.toString(ex.getStackTrace()));
        ErrorDTO errorDTO = new ErrorDTO(HttpStatus.CONFLICT, ex.getClass().getName(), ex.getMessage());
        return new ResponseEntity<>(errorDTO, new HttpHeaders(), errorDTO.getHttpStatus());
    }

    @ExceptionHandler(BadRequestException.class)
    protected ResponseEntity<Object> handleBadRequest(BadRequestException ex) {
        log.warn("Bad request exception stack: {} ", Arrays.toString(ex.getStackTrace()));
        ErrorDTO errorDTO = new ErrorDTO(HttpStatus.BAD_REQUEST, ex.getClass().getName(), ex.getMessage());
        return new ResponseEntity<>(errorDTO, new HttpHeaders(), errorDTO.getHttpStatus());
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.warn("Bad request exception stack: {} ", Arrays.toString(ex.getStackTrace()));
        ErrorDTO errorDTO = new ErrorDTO(HttpStatus.BAD_REQUEST, ex.getClass().getName(), ex.getMessage());
        return new ResponseEntity<>(errorDTO, new HttpHeaders(), errorDTO.getHttpStatus());
    }

    @ExceptionHandler(InvalidFormatException.class)
    protected ResponseEntity<Object> handleNotValidArgument(InvalidFormatException ex) {
        log.warn("Bad request exception stack: {} ", Arrays.toString(ex.getStackTrace()));
        ErrorDTO errorDTO = new ErrorDTO(HttpStatus.BAD_REQUEST, ex.getClass().getName(), ex.getMessage());
        return new ResponseEntity<>(errorDTO, new HttpHeaders(), errorDTO.getHttpStatus());
    }

    @ExceptionHandler(DateTimeParseException.class)
    protected ResponseEntity<Object> handleDateTimeParseException(DateTimeParseException ex) {
        log.warn("Date time parse exception stack: {} ", Arrays.toString(ex.getStackTrace()));
        ErrorDTO errorDTO = new ErrorDTO(HttpStatus.BAD_REQUEST, ex.getClass().getName(), ex.getMessage());
        return new ResponseEntity<>(errorDTO, new HttpHeaders(), errorDTO.getHttpStatus());
    }

    @ExceptionHandler(AccessDeniedException.class)
    protected ResponseEntity<Object> handleUnauthorizedException(AccessDeniedException ex) {
        log.error("Unauthorized exception stack: {} ", Arrays.toString(ex.getStackTrace()));
        ErrorDTO errorDTO = new ErrorDTO(HttpStatus.FORBIDDEN, ex.getClass().getName(), ex.getMessage());
        return new ResponseEntity<>(errorDTO, new HttpHeaders(), errorDTO.getHttpStatus());
    }

    @ExceptionHandler(BadCredentialsException.class)
    protected ResponseEntity<Object> handleBadCredentialsException(BadCredentialsException ex) {
        log.error("Bad credentials exception stack: {} ", Arrays.toString(ex.getStackTrace()));
        ErrorDTO errorDTO = new ErrorDTO(HttpStatus.UNAUTHORIZED, ex.getClass().getName(), ex.getMessage());
        return new ResponseEntity<>(errorDTO, new HttpHeaders(), errorDTO.getHttpStatus());
    }

    @ExceptionHandler(SQLException.class)
    protected ResponseEntity<Object> handleSqlException(SQLException ex) {
        log.warn("Invalid order exception stack: {} ", Arrays.toString(ex.getStackTrace()));
        ErrorDTO errorDTO = new ErrorDTO(HttpStatus.BAD_REQUEST, ex.getClass().getName(), ex.getMessage());
        return new ResponseEntity<>(errorDTO, new HttpHeaders(), errorDTO.getHttpStatus());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> handleConstraintViolation(
            final DataIntegrityViolationException ex, final WebRequest request) {
        log.warn("Sql exception stack: {} ", Arrays.toString(ex.getStackTrace()));
        List<String> errors = new ArrayList<>();
        errors.add("Entity dependency broken.");
        ErrorDTO errorDTO =
                new ErrorDTO(HttpStatus.CONFLICT, ex.getLocalizedMessage(), errors.toString());
        return new ResponseEntity<>(errorDTO, new HttpHeaders(), errorDTO.getHttpStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDTO> handleAll(Exception ex) {
        log.error("Server exception stack: {} ", Arrays.toString(ex.getStackTrace()));
        log.info("Exception {}", ex);
        ErrorDTO errorDto =
                new ErrorDTO(HttpStatus.INTERNAL_SERVER_ERROR, ex.getLocalizedMessage(), "error occurred");
        return new ResponseEntity<>(errorDto, new HttpHeaders(), errorDto.getHttpStatus());
    }
}
