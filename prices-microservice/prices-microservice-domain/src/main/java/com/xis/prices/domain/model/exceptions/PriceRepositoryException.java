package com.xis.prices.domain.model.exceptions;

import com.xis.prices.domain.model.error.ErrorCode;
import lombok.Getter;

/**
 * Price repository exception
 *
 * @author XIS
 */
@Getter
public class PriceRepositoryException extends RuntimeException {

    private final ErrorCode errorCode;

    private final String[] errorArguments;

    /**
     * Price repository exception constructor
     *
     * @param message Message
     */
    public PriceRepositoryException(final String message, final ErrorCode errorCode, final String[] errorArguments) {
        super(message);

        this.errorCode = errorCode;
        this.errorArguments = errorArguments;
    }

}
