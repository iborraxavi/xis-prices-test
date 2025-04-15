package com.xis.prices.api.controller.error;

import com.xis.prices.domain.model.error.ErrorCode;
import com.xis.prices.domain.model.exceptions.PriceRepositoryException;
import com.xis.prices.domain.model.exceptions.SearchNotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContext;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ServerWebExchange;

import java.util.Locale;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RestErrorHandlerTest {

    private static final String ERROR_MESSAGE = "Error message";

    private static final String CONSTRAINT_PROPERTY_PATH = "search.productId";

    private static final String CONSTRAINT_MESSAGE = "Constraint message error";

    private static final Locale DEFAULT_LOCALE = Locale.UK;

    @Mock
    private LocaleContext localeContext;

    @Mock
    private ServerWebExchange exchange;

    @Mock
    private MessageSource messageSource;

    @InjectMocks
    private RestErrorHandler restErrorHandler;

    @Test
    @DisplayName("Given SearchNotFoundException when handle SearchNotFoundException should return expected response")
    void givenSearchNotFoundException_whenHandleSearchNotFoundException_shouldReturnExpectedResponse() {
        final SearchNotFoundException searchNotFoundException = mock(SearchNotFoundException.class);

        when(searchNotFoundException.getMessage()).thenReturn(ERROR_MESSAGE);
        when(exchange.getLocaleContext()).thenReturn(localeContext);
        when(localeContext.getLocale()).thenReturn(DEFAULT_LOCALE);
        when(searchNotFoundException.getErrorCode()).thenReturn(ErrorCode.PRICE_SEARCH_NOT_FOUND);
        when(messageSource.getMessage(ErrorCode.PRICE_SEARCH_NOT_FOUND.getCode(), null, DEFAULT_LOCALE)).thenReturn(ERROR_MESSAGE);

        var result = restErrorHandler.handleSearchNotFoundException(searchNotFoundException, exchange);

        assertNotNull(result);
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals(String.valueOf(HttpStatus.NOT_FOUND.value()), result.getBody().getCode());
        assertEquals(ERROR_MESSAGE, result.getBody().getMessage());
        assertTrue(CollectionUtils.isEmpty(result.getBody().getErrors()));

        verify(messageSource, only()).getMessage(ErrorCode.PRICE_SEARCH_NOT_FOUND.getCode(), null, DEFAULT_LOCALE);
    }

    @Test
    @DisplayName("Given PriceRepositoryException when handle PriceRepositoryException should return expected response")
    void givenPriceRepositoryException_whenPriceRepositoryException_shouldReturnExpectedResponse() {
        final PriceRepositoryException priceRepositoryException = mock(PriceRepositoryException.class);

        when(priceRepositoryException.getMessage()).thenReturn(ERROR_MESSAGE);
        when(exchange.getLocaleContext()).thenReturn(localeContext);
        when(localeContext.getLocale()).thenReturn(DEFAULT_LOCALE);
        when(priceRepositoryException.getErrorCode()).thenReturn(ErrorCode.PRICE_REPOSITORY_EXCEPTION);
        when(messageSource.getMessage(ErrorCode.PRICE_REPOSITORY_EXCEPTION.getCode(), null, DEFAULT_LOCALE)).thenReturn(ERROR_MESSAGE);

        var result = restErrorHandler.handlePriceRepositoryException(priceRepositoryException, exchange);

        assertNotNull(result);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()), result.getBody().getCode());
        assertEquals(ERROR_MESSAGE, result.getBody().getMessage());
        assertTrue(CollectionUtils.isEmpty(result.getBody().getErrors()));

        verify(messageSource, only()).getMessage(ErrorCode.PRICE_REPOSITORY_EXCEPTION.getCode(), null, DEFAULT_LOCALE);
    }

    @Test
    @DisplayName("Given ConstraintViolationException when handle ConstraintViolationException should return expected response")
    void givenConstraintViolationException_whenHandleConstraintViolationException_shouldReturnExpectedResponse() {
        final ConstraintViolationException constraintViolationException = mock(ConstraintViolationException.class);
        final ConstraintViolation<?> constraintViolation = mock(ConstraintViolation.class);

        when(constraintViolationException.getMessage()).thenReturn(ERROR_MESSAGE);
        when(constraintViolation.getPropertyPath()).thenReturn(PathImpl.createPathFromString(CONSTRAINT_PROPERTY_PATH));
        when(constraintViolation.getMessage()).thenReturn(CONSTRAINT_MESSAGE);
        when(constraintViolationException.getConstraintViolations()).thenReturn(Set.of(constraintViolation));

        var result = restErrorHandler.handleConstraintViolationException(constraintViolationException);

        assertNotNull(result);
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals(String.valueOf(HttpStatus.BAD_REQUEST.value()), result.getBody().getCode());
        assertEquals(ERROR_MESSAGE, result.getBody().getMessage());
        assertFalse(CollectionUtils.isEmpty(result.getBody().getErrors()));
        assertEquals(1, result.getBody().getErrors().size());
        assertEquals(CONSTRAINT_PROPERTY_PATH, result.getBody().getErrors().getFirst().getField());
        assertEquals(CONSTRAINT_MESSAGE, result.getBody().getErrors().getFirst().getMessage());

        verifyNoInteractions(messageSource);
    }

    @Test
    @DisplayName("Given Exception when handle Exception should return expected response")
    void givenException_whenHandleException_shouldReturnExpectedResponse() {
        final RuntimeException runtimeException = mock(RuntimeException.class);

        when(runtimeException.getMessage()).thenReturn(ERROR_MESSAGE);

        var result = restErrorHandler.handleException(runtimeException);

        assertNotNull(result);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()), result.getBody().getCode());
        assertEquals(ERROR_MESSAGE, result.getBody().getMessage());
        assertTrue(CollectionUtils.isEmpty(result.getBody().getErrors()));

        verifyNoInteractions(messageSource);
    }

}
