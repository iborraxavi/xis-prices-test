package com.xis.prices.domain.repository;

import com.xis.prices.domain.model.Price;
import com.xis.prices.domain.model.PriceRequest;
import reactor.core.publisher.Flux;

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
    Flux<Price> search(final PriceRequest priceRequest);

}
