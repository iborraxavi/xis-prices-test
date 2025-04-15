package com.xis.prices.infrastructure.mapper;

import com.xis.prices.infrastructure.entity.PriceEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class PriceInfrastructureMapperTest {

    private static final Long ID = 11111L;

    private static final Long PRODUCT_ID = 10009L;

    private static final Integer PRICE_LIST = 32;

    private static final LocalDateTime START_DATE = LocalDateTime.now();

    private static final LocalDateTime END_DATE = LocalDateTime.now().plusDays(1);

    private static final Integer BRAND_ID = 1234;

    private static final Double PRICE = 12.78;

    private static final String CURRENCY = "EUR";

    private static final Integer PRIORITY = 2;

    private final PriceInfrastructureMapper priceInfrastructureMapper = Mappers.getMapper(PriceInfrastructureMapper.class);

    @Test
    @DisplayName("Given price entity when map to domain should return expected response")
    void givenPriceEntity_whenMapToDomain_shouldReturnExpectedResponse() {
        var result = priceInfrastructureMapper.toDomain(buildPriceEntity());

        assertNotNull(result);
        assertEquals(ID, result.id());
        assertEquals(BRAND_ID, result.brandId());
        assertEquals(PRODUCT_ID, result.productId());
        assertEquals(PRICE_LIST, result.priceList());
        assertEquals(START_DATE, result.startDate());
        assertEquals(END_DATE, result.endDate());
        assertEquals(PRICE, result.price());
        assertEquals(CURRENCY, result.currency());
        assertEquals(PRIORITY, result.priority());
    }

    private PriceEntity buildPriceEntity() {
        return PriceEntity.builder()
                .id(ID)
                .brandId(BRAND_ID)
                .productId(PRODUCT_ID)
                .priceList(PRICE_LIST)
                .startDate(START_DATE)
                .endDate(END_DATE)
                .price(PRICE)
                .currency(CURRENCY)
                .priority(PRIORITY)
                .build();
    }
}
