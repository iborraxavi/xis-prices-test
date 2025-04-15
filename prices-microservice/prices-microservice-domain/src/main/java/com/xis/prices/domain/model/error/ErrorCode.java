package com.xis.prices.domain.model.error;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ErrorCode {
    PRICE_REPOSITORY_EXCEPTION("price.repository.exception"),
    PRICE_SEARCH_NOT_FOUND("price.search.not-found");

    private final String code;

}
