package com.xis.prices.infrastructure.repository.impl;

import com.xis.prices.domain.model.Price;
import com.xis.prices.domain.model.PriceRequest;
import com.xis.prices.domain.model.error.ErrorCode;
import com.xis.prices.domain.model.exceptions.PriceRepositoryException;
import com.xis.prices.domain.repository.PriceRepository;
import com.xis.prices.infrastructure.mapper.PriceInfrastructureMapper;
import com.xis.prices.infrastructure.repository.jpa.PriceReactiveCrudRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Default price repository
 *
 * @author XIS
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DefaultPriceRepository implements PriceRepository {

    private static final String ERROR_MESSAGE = "Repository unexpected error for application date: %s, product id: %s and brand id: %s";

    private final PriceInfrastructureMapper priceInfrastructureMapper;

    private final PriceReactiveCrudRepository priceReactiveCrudRepository;

    /**
     * Search price
     *
     * @param priceRequest Price request
     * @return Price
     */
    @Override
    public Flux<Price> search(final PriceRequest priceRequest) {
        return priceReactiveCrudRepository.search(priceRequest.applicationDate(), priceRequest.productId(), priceRequest.brandId())
                .map(priceInfrastructureMapper::toDomain)
                .onErrorResume(error -> {
                    log.error("Repository unexpected error: {}", error.getMessage(), error);
                    return Mono.error(new PriceRepositoryException(
                            ERROR_MESSAGE.formatted(priceRequest.applicationDate(), priceRequest.productId(), priceRequest.brandId()),
                            ErrorCode.PRICE_REPOSITORY_EXCEPTION,
                            new String[]{priceRequest.applicationDate().toString(), priceRequest.productId().toString(), priceRequest.brandId().toString()}));
                });
    }

}
