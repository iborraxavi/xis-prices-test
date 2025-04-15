package com.xis.prices.api.controller;

import com.xis.prices.api.mapper.PricesApiMapper;
import com.xis.prices.domain.service.PriceService;
import com.xis.prices.pricesapi.controller.PriceApi;
import com.xis.prices.pricesapi.dto.PricesSearchResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Date;

/**
 * Prices controller
 *
 * @author XIS
 */
@RequiredArgsConstructor
@RestController
public class PricesController implements PriceApi {

    private final PricesApiMapper pricesApiMapper;

    private final PriceService priceService;

    /**
     * Search price
     *
     * @param applicationDate Application date
     * @param productId       Product id
     * @param brandId         Brand id
     * @param exchange        Server web exchange
     * @return Price search response
     */
    @Override
    public Mono<ResponseEntity<PricesSearchResponse>> prices(
            @NotNull @Valid final Date applicationDate,
            @NotNull @Min(1L) @Valid final Long productId,
            @NotNull @Min(1L) @Valid final Integer brandId,
            final ServerWebExchange exchange) {
        return Mono.just(pricesApiMapper.toDomainRequest(applicationDate, productId, brandId))
                .flatMap(priceService::searchPrice)
                .map(price -> ResponseEntity.ok(pricesApiMapper.toPricesSearchResponse(price)));
    }

}
