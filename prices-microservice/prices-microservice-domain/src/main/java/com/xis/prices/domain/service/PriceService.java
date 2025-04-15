package com.xis.prices.domain.service;

import com.xis.prices.domain.model.Price;
import com.xis.prices.domain.model.PriceRequest;
import jakarta.validation.Valid;
import reactor.core.publisher.Mono;

/**
 * Price service
 *
 * @author XIS
 */
public interface PriceService {

    /**
     * Search price
     *
     * @param priceRequest Price request
     * @return Price
     */
    Mono<Price> searchPrice(@Valid PriceRequest priceRequest);

}
