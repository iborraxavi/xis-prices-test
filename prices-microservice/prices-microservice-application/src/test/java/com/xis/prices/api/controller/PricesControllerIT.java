package com.xis.prices.api.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

@ActiveProfiles("test")
@AutoConfigureWebTestClient(timeout = "3600000")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PricesControllerIT {

    private static final String PRICES_PATH = "/prices?applicationDate=%s&productId=%s&brandId=%s";

    private static final String INVALID_DATE = "2024-06-14T10:00:00Z";

    private static final String FIRST_DATE = "2020-06-14T10:00:00Z";

    private static final String SECOND_DATE = "2020-06-14T16:00:00Z";

    private static final String THIRD_DATE = "2020-06-14T21:00:00Z";

    private static final String FOURTH_DATE = "2020-06-15T10:00:00Z";

    private static final String FIFTH_DATE = "2020-06-16T21:00:00Z";

    private static final Long PRODUCT_ID = 35455L;

    private static final Long INVALID_PRODUCT_ID = -1L;

    private static final Integer BRAND_ID = 1;

    private static final Integer INVALID_BRAND_ID = -1;

    @Autowired
    private WebTestClient webTestClient;

    @Test
    @DisplayName("Given invalid product id when error should return expected error")
    void givenInvalidProductId_whenError_shouldReturnExpectedError() {
        webTestClient
                .get()
                .uri(String.format(PRICES_PATH, FIRST_DATE, INVALID_PRODUCT_ID, BRAND_ID))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isBadRequest()
                .expectBody()
                .jsonPath("$.code").isEqualTo(String.valueOf(HttpStatus.BAD_REQUEST.value()))
                .jsonPath("$.message").isEqualTo("prices.productId: debe ser mayor que o igual a 1")
                .jsonPath("$.errors.length()").isEqualTo(1)
                .jsonPath("$.errors[0].field").isEqualTo("prices.productId")
                .jsonPath("$.errors[0].message").isEqualTo("debe ser mayor que o igual a 1");
    }

    @Test
    @DisplayName("Given invalid brand id when error should return expected error")
    void givenInvalidInvalidBrandId_whenError_shouldReturnExpectedError() {
        webTestClient
                .get()
                .uri(String.format(PRICES_PATH, FIRST_DATE, PRODUCT_ID, INVALID_BRAND_ID))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isBadRequest()
                .expectBody()
                .jsonPath("$.code").isEqualTo(String.valueOf(HttpStatus.BAD_REQUEST.value()))
                .jsonPath("$.message").isEqualTo("prices.brandId: debe ser mayor que o igual a 1")
                .jsonPath("$.errors.length()").isEqualTo(1)
                .jsonPath("$.errors[0].field").isEqualTo("prices.brandId")
                .jsonPath("$.errors[0].message").isEqualTo("debe ser mayor que o igual a 1");
    }

    @Test
    @DisplayName("Given invalid application date when search not found results should return expected error")
    void givenInvalidApplicationDate_whenSearchNotFoundResults_shouldReturnExpectedError() {
        webTestClient
                .get()
                .uri(String.format(PRICES_PATH, INVALID_DATE, PRODUCT_ID, BRAND_ID))
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.ACCEPT_LANGUAGE, "en-GB")
                .exchange()
                .expectStatus()
                .isNotFound()
                .expectBody()
                .jsonPath("$.code").isEqualTo(String.valueOf(HttpStatus.NOT_FOUND.value()))
                .jsonPath("$.message").isEqualTo("Price not found with application date: 2024-06-14T10:00, product id: 35455 and brand id: 1");
    }

    @Test
    @DisplayName("Given first date when success should return expected response")
    void givenFirstDate_whenSuccess_shouldReturnExpectedResponse() {
        webTestClient
                .get()
                .uri(String.format(PRICES_PATH, FIRST_DATE, PRODUCT_ID, BRAND_ID))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .jsonPath("$.productId").isEqualTo(PRODUCT_ID)
                .jsonPath("$.priceList").isEqualTo(1)
                .jsonPath("$.startDate").isEqualTo("2020-06-14 00:00:00")
                .jsonPath("$.endDate").isEqualTo("2020-12-31 23:59:59")
                .jsonPath("$.price").isEqualTo(35.5);
    }

    @Test
    @DisplayName("Given second date when success should return expected response")
    void givenSecondDate_whenSuccess_shouldReturnExpectedResponse() {
        webTestClient
                .get()
                .uri(String.format(PRICES_PATH, SECOND_DATE, PRODUCT_ID, BRAND_ID))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .jsonPath("$.productId").isEqualTo(PRODUCT_ID)
                .jsonPath("$.priceList").isEqualTo(2)
                .jsonPath("$.startDate").isEqualTo("2020-06-14 15:00:00")
                .jsonPath("$.endDate").isEqualTo("2020-06-14 18:30:00")
                .jsonPath("$.price").isEqualTo(25.45);
    }

    @Test
    @DisplayName("Given third date when success should return expected response")
    void givenThirdDate_whenSuccess_shouldReturnExpectedResponse() {
        webTestClient
                .get()
                .uri(String.format(PRICES_PATH, THIRD_DATE, PRODUCT_ID, BRAND_ID))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .jsonPath("$.productId").isEqualTo(PRODUCT_ID)
                .jsonPath("$.priceList").isEqualTo(1)
                .jsonPath("$.startDate").isEqualTo("2020-06-14 00:00:00")
                .jsonPath("$.endDate").isEqualTo("2020-12-31 23:59:59")
                .jsonPath("$.price").isEqualTo(35.5);
    }

    @Test
    @DisplayName("Given fourth date when success should return expected response")
    void givenFourthDate_whenSuccess_shouldReturnExpectedResponse() {
        webTestClient
                .get()
                .uri(String.format(PRICES_PATH, FOURTH_DATE, PRODUCT_ID, BRAND_ID))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .jsonPath("$.productId").isEqualTo(PRODUCT_ID)
                .jsonPath("$.priceList").isEqualTo(3)
                .jsonPath("$.startDate").isEqualTo("2020-06-15 00:00:00")
                .jsonPath("$.endDate").isEqualTo("2020-06-15 11:00:00")
                .jsonPath("$.price").isEqualTo(30.5);
    }

    @Test
    @DisplayName("Given fifth date when success should return expected response")
    void givenFifthDate_whenSuccess_shouldReturnExpectedResponse() {
        webTestClient
                .get()
                .uri(String.format(PRICES_PATH, FIFTH_DATE, PRODUCT_ID, BRAND_ID))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .jsonPath("$.productId").isEqualTo(PRODUCT_ID)
                .jsonPath("$.priceList").isEqualTo(4)
                .jsonPath("$.startDate").isEqualTo("2020-06-15 16:00:00")
                .jsonPath("$.endDate").isEqualTo("2020-12-31 23:59:59")
                .jsonPath("$.price").isEqualTo(38.95);
    }

}
