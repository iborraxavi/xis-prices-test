package com.xis.prices.domain.repository;

import com.xis.prices.domain.model.Price;
import com.xis.prices.domain.model.PriceRequest;
import reactor.core.publisher.Mono;

/**
 * Price repository
 *
 * @author XIS
 */
public interface PriceRepository {

    /**
     * Search price
     *
     * @param priceRequest Price request
     * @return Price
     */
    Mono<Price> search(PriceRequest priceRequest);

}
