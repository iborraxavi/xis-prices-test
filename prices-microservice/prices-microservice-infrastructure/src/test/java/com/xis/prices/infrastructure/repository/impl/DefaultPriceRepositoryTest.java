package com.xis.prices.infrastructure.repository.impl;

import com.xis.prices.domain.model.Price;
import com.xis.prices.domain.model.PriceRequest;
import com.xis.prices.domain.model.exceptions.PriceRepositoryException;
import com.xis.prices.infrastructure.entity.PriceEntity;
import com.xis.prices.infrastructure.mapper.PriceInfrastructureMapper;
import com.xis.prices.infrastructure.repository.jpa.PriceReactiveCrudRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DefaultPriceRepositoryTest {

    private static final LocalDateTime APPLICATION_DATE = LocalDateTime.now();

    private static final Long PRODUCT_ID = 1001L;

    private static final Integer BRAND_ID = 123;

    @Mock
    private PriceInfrastructureMapper priceInfrastructureMapper;

    @Mock
    private PriceReactiveCrudRepository priceReactiveCrudRepository;

    @InjectMocks
    private DefaultPriceRepository defaultPriceRepository;

    @Test
    @DisplayName("Given price request when success should return expected response")
    void givenPriceRequest_whenSuccess_shouldReturnExpectedResponse() {
        final PriceRequest priceRequest = buildPriceRequest();
        final PriceEntity priceEntity = mock(PriceEntity.class);
        final Price price = mock(Price.class);

        when(priceReactiveCrudRepository.search(APPLICATION_DATE, PRODUCT_ID, BRAND_ID)).thenReturn(Flux.just(priceEntity));
        when(priceInfrastructureMapper.toDomain(priceEntity)).thenReturn(price);

        var result = defaultPriceRepository.search(priceRequest);

        StepVerifier.create(result)
                .expectNext(price)
                .verifyComplete();

        verify(priceReactiveCrudRepository, only()).search(APPLICATION_DATE, PRODUCT_ID, BRAND_ID);
        verify(priceInfrastructureMapper, only()).toDomain(priceEntity);
    }

    @Test
    @DisplayName("Given price request when error should return expected error")
    void givenPriceRequest_whenError_shouldReturnExpectedError() {
        final PriceRequest priceRequest = buildPriceRequest();
        final PriceEntity priceEntity = mock(PriceEntity.class);
        final Price price = mock(Price.class);

        when(priceReactiveCrudRepository.search(APPLICATION_DATE, PRODUCT_ID, BRAND_ID)).thenReturn(Flux.error(new RuntimeException("Unexpected error")));

        var result = defaultPriceRepository.search(priceRequest);

        StepVerifier.create(result)
                .expectError(PriceRepositoryException.class)
                .verify();

        verify(priceReactiveCrudRepository, only()).search(APPLICATION_DATE, PRODUCT_ID, BRAND_ID);
        verifyNoInteractions(priceInfrastructureMapper);
    }

    private PriceRequest buildPriceRequest() {
        return new PriceRequest(APPLICATION_DATE, PRODUCT_ID, BRAND_ID);
    }

}
