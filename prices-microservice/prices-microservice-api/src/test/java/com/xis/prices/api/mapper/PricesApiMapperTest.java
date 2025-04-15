package com.xis.prices.api.mapper;

import com.xis.prices.domain.model.Price;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class PricesApiMapperTest {

    private static final Long ID = 11111L;

    private static final Long PRODUCT_ID = 10009L;

    private static final Integer PRICE_LIST = 32;

    private static final LocalDateTime START_DATE = LocalDateTime.now();

    private static final LocalDateTime END_DATE = LocalDateTime.now().plusDays(1);

    private static final Integer BRAND_ID = 1234;

    private static final Double PRICE = 12.78;

    private static final String CURRENCY = "EUR";

    private static final Integer PRIORITY = 2;

    private static final Date APPLICATION_DATE;

    static {
        try {
            APPLICATION_DATE = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2024-09-21 01:09:22");
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    private final PricesApiMapper pricesApiMapper = Mappers.getMapper(PricesApiMapper.class);

    @Test
    @DisplayName("Given application date and product id and brand id when map to request domain should return expected response")
    void givenApplicationDateAndProductIdAndBrandId_whenMapToRequestDomain_shouldReturnExpectedResponse() {
        var result = pricesApiMapper.toDomainRequest(APPLICATION_DATE, PRODUCT_ID, BRAND_ID);

        assertNotNull(result);
        assertEquals(APPLICATION_DATE.getTime(), result.applicationDate().toInstant(ZoneOffset.UTC).toEpochMilli());
        assertEquals(PRODUCT_ID, result.productId());
        assertEquals(BRAND_ID, result.brandId());
    }

    @Test
    @DisplayName("Given price when map to prices search response should return expected response")
    void givenPrice_whenMapToPricesSearchResponse_shouldReturnExpectedResponse() {
        var result = pricesApiMapper.toPricesSearchResponse(buildPrice());

        assertNotNull(result);
        assertEquals(PRODUCT_ID, result.getProductId());
        assertEquals(BRAND_ID, result.getBrandId());
        assertEquals(PRICE_LIST, result.getPriceList());
        assertEquals(START_DATE.toInstant(ZoneOffset.UTC).toEpochMilli(), result.getStartDate().getTime());
        assertEquals(END_DATE.toInstant(ZoneOffset.UTC).toEpochMilli(), result.getEndDate().getTime());
        assertEquals(PRICE, result.getPrice());
    }

    private Price buildPrice() {
        return new Price(ID, BRAND_ID, PRODUCT_ID, PRICE_LIST, START_DATE, END_DATE, PRICE, CURRENCY, PRIORITY);
    }

}
