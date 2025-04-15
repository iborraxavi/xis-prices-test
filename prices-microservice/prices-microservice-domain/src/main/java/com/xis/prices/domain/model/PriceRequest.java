package com.xis.prices.domain.model;

import jakarta.validation.constraints.Min;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;

/**
 * Price request record
 *
 * @param applicationDate Application date
 * @param productId       Product ID
 * @param brandId         Brand ID
 */
@Validated
public record PriceRequest(
        LocalDateTime applicationDate,

        @Min(value = 1, message = "Product ID should be greater than 0")
        Long productId,

        @Min(value = 1, message = "Brand ID should be greater than 0")
        Integer brandId) {
}
