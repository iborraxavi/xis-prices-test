package com.xis.prices.api.controller.error;

import com.xis.prices.domain.model.error.ErrorCode;
import com.xis.prices.domain.model.exceptions.PriceRepositoryException;
import com.xis.prices.domain.model.exceptions.SearchNotFoundException;
import com.xis.prices.pricesapi.dto.Error;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ServerWebExchange;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Set;

/**
 * Rest error handler
 *
 * @author XIS
 */
@Slf4j
@RequiredArgsConstructor
@ControllerAdvice
public class RestErrorHandler {

    private final MessageSource messageSource;

    /**
     * Handle search not found exception
     *
     * @param ex Search not found exception
     * @return Error
     */
    @ExceptionHandler(SearchNotFoundException.class)
    public ResponseEntity<Error> handleSearchNotFoundException(final SearchNotFoundException ex, final ServerWebExchange exchange) {
        log.error("Search not found error: {}", ex.getMessage());

        final HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        return ResponseEntity.status(httpStatus).body(buildError(
                httpStatus.value(),
                getI18nMessage(ex.getErrorCode(), ex.getErrorArguments(), ex.getMessage(), exchange.getLocaleContext().getLocale())));
    }

    /**
     * Handle price repository exception
     *
     * @param ex Price repository exception
     * @return Error
     */
    @ExceptionHandler(PriceRepositoryException.class)
    public ResponseEntity<Error> handlePriceRepositoryException(final PriceRepositoryException ex, final ServerWebExchange exchange) {
        log.error("Price repository exception error", ex);

        final HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        return ResponseEntity.status(httpStatus).body(buildError(
                httpStatus.value(),
                getI18nMessage(ex.getErrorCode(), ex.getErrorArguments(), ex.getMessage(), exchange.getLocaleContext().getLocale())));
    }

    /**
     * Handle constraint violation exception
     *
     * @param ex Constraint violation exception
     * @return Error
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Error> handleConstraintViolationException(final ConstraintViolationException ex) {
        log.error("Constraint violation exception error", ex);

        final HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(httpStatus).body(buildError(httpStatus.value(), ex.getMessage(), ex.getConstraintViolations()));
    }

    /**
     * Handle generic exception
     *
     * @param ex Exception
     * @return Error
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Error> handleException(final Exception ex) {
        log.error("Generic exception error", ex);

        final HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        return ResponseEntity.status(httpStatus).body(buildError(httpStatus.value(), ex.getMessage()));
    }

    private Error buildError(final int code, final String message) {
        return buildError(code, message, null);
    }

    private Error buildError(final int code, final String message, final Set<ConstraintViolation<?>> constraintViolations) {
        return new Error()
                .code(String.valueOf(code))
                .message(message)
                .errors(mapConstraintViolations(constraintViolations));
    }

    private List<com.xis.prices.pricesapi.dto.ConstraintViolation> mapConstraintViolations(final Set<ConstraintViolation<?>> constraintViolations) {
        if (constraintViolations == null) {
            return Collections.emptyList();
        }

        return constraintViolations.stream()
                .map(this::mapConstraintViolation)
                .toList();
    }

    private com.xis.prices.pricesapi.dto.ConstraintViolation mapConstraintViolation(final ConstraintViolation<?> constraintViolation) {
        return new com.xis.prices.pricesapi.dto.ConstraintViolation()
                .field(constraintViolation.getPropertyPath().toString())
                .message(constraintViolation.getMessage());
    }

    private String getI18nMessage(
            final ErrorCode errorCode,
            final String[] errorArguments,
            final String defaultMessage,
            final Locale locale) {
        try {
            return messageSource.getMessage(errorCode.getCode(), errorArguments, locale);
        } catch (NoSuchMessageException e) {
            log.error("No such message with key: {}", errorCode.getCode());
            return defaultMessage;
        }
    }

}
