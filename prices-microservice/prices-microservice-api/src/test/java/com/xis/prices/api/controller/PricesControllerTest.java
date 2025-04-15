package com.xis.prices.api.controller;

import com.xis.prices.api.mapper.PricesApiMapper;
import com.xis.prices.domain.model.Price;
import com.xis.prices.domain.model.PriceRequest;
import com.xis.prices.domain.service.PriceService;
import com.xis.prices.pricesapi.dto.PricesSearchResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PricesControllerTest {

    private static final Long PRODUCT_ID = 10009L;

    private static final Integer BRAND_ID = 1234;

    private static final Date APPLICATION_DATE = new Date();

    @Mock
    private PricesApiMapper pricesApiMapper;

    @Mock
    private PriceService priceService;

    @Mock
    private ServerWebExchange serverWebExchange;

    @InjectMocks
    private PricesController pricesController;

    @Test
    @DisplayName("Given application date and product id and brand id when search price then return expected response")
    void givenApplicationDateAndProductIdAndBrandId_whenSearchPrice_thenReturnExpectedResponse() {
        final PriceRequest priceRequest = mock(PriceRequest.class);
        final Price price = mock(Price.class);
        final PricesSearchResponse pricesSearchResponse = mock(PricesSearchResponse.class);

        when(pricesApiMapper.toDomainRequest(APPLICATION_DATE, PRODUCT_ID, BRAND_ID)).thenReturn(priceRequest);
        when(priceService.searchPrice(priceRequest)).thenReturn(Mono.just(price));
        when(pricesApiMapper.toPricesSearchResponse(price)).thenReturn(pricesSearchResponse);

        var result = pricesController.prices(APPLICATION_DATE, PRODUCT_ID, BRAND_ID, serverWebExchange);

        StepVerifier.create(result)
                .assertNext(pricesSearchResponseResponseEntity -> {
                    assertNotNull(pricesSearchResponseResponseEntity);
                    assertEquals(HttpStatus.OK, pricesSearchResponseResponseEntity.getStatusCode());
                    assertEquals(pricesSearchResponseResponseEntity.getBody(), pricesSearchResponse);
                })
                .verifyComplete();

        verify(pricesApiMapper, times(1)).toDomainRequest(APPLICATION_DATE, PRODUCT_ID, BRAND_ID);
        verify(pricesApiMapper, times(1)).toPricesSearchResponse(price);
        verifyNoMoreInteractions(pricesApiMapper);
        verify(priceService, only()).searchPrice(priceRequest);
    }

}
