package com.xis.prices.domain.model.exceptions;

import com.xis.prices.domain.model.error.ErrorCode;
import lombok.Getter;

/**
 * Search not found exception
 *
 * @author XIS
 */
@Getter
public class SearchNotFoundException extends RuntimeException {

    private final ErrorCode errorCode;

    private final String[] errorArguments;

    /**
     * Search not found exception
     *
     * @param message Message
     */
    public SearchNotFoundException(final String message, final ErrorCode errorCode, final String[] errorArguments) {
        super(message);

        this.errorCode = errorCode;
        this.errorArguments = errorArguments;
    }

}
