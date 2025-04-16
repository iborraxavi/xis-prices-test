package com.xis.prices.domain.service;

import com.xis.prices.domain.model.Price;
import com.xis.prices.domain.model.PriceRequest;
import com.xis.prices.domain.model.exceptions.SearchNotFoundException;
import com.xis.prices.domain.repository.PriceRepository;
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
class DefaultPriceServiceTest {

    private static final LocalDateTime APPLICATION_DATE = LocalDateTime.now();

    private static final Long PRODUCT_ID = 1L;

    private static final Integer BRAND_ID = 1;

    @Mock
    private PriceRepository priceRepository;

    @InjectMocks
    private DefaultPriceService defaultPriceService;

    @Test
    @DisplayName("Given price request when search price should return expected response")
    void givenPriceRequest_whenSearchPrice_shouldReturnExpectedResponse() {
        final PriceRequest priceRequest = mock(PriceRequest.class);
        final Price price = mock(Price.class);

        when(priceRepository.search(priceRequest)).thenReturn(Flux.just(price));
        when(priceRequest.applicationDate()).thenReturn(APPLICATION_DATE);
        when(priceRequest.productId()).thenReturn(PRODUCT_ID);
        when(priceRequest.brandId()).thenReturn(BRAND_ID);

        var result = defaultPriceService.searchPrice(priceRequest);

        StepVerifier.create(result)
                .expectNext(price)
                .verifyComplete();

        verify(priceRepository, only()).search(priceRequest);
    }

    @Test
    @DisplayName("Given price request when empty search price result should return expected response")
    void givenPriceRequest_whenEmptySearchPriceResult_shouldReturnExpectedResponse() {
        final PriceRequest priceRequest = mock(PriceRequest.class);

        when(priceRepository.search(priceRequest)).thenReturn(Flux.empty());
        when(priceRequest.applicationDate()).thenReturn(APPLICATION_DATE);
        when(priceRequest.productId()).thenReturn(PRODUCT_ID);
        when(priceRequest.brandId()).thenReturn(BRAND_ID);

        var result = defaultPriceService.searchPrice(priceRequest);

        StepVerifier.create(result)
                .expectError(SearchNotFoundException.class)
                .verify();

        verify(priceRepository, only()).search(priceRequest);
    }

}
