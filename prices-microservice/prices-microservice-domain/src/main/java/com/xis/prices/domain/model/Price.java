package com.xis.prices.domain.model;

import java.time.LocalDateTime;

/**
 * Price record
 *
 * @param id        ID
 * @param brandId   Brand ID
 * @param productId Product ID
 * @param priceList Price list
 * @param startDate Start date
 * @param endDate   End date
 * @param price     Price
 * @param currency  Currency
 * @param priority  Priority
 */
public record Price(
        Long id,
        Integer brandId,
        Long productId,
        Integer priceList,
        LocalDateTime startDate,
        LocalDateTime endDate,
        Double price,
        String currency,
        Integer priority) implements Comparable<Price> {

    @Override
    public int compareTo(final Price otherPrice) {
        return otherPrice.priority.compareTo(this.priority);
    }

}
