package com.xis.prices.infrastructure.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Price entity
 *
 * @author XIS
 */
@Builder
@AllArgsConstructor
@Getter
@Table(name = "PRICES")
public class PriceEntity implements Serializable {

    @Id
    @Column("ID")
    private final Long id;

    @Column("BRAND_ID")
    private final Integer brandId;

    @Column("PRODUCT_ID")
    private final Long productId;

    @Column("PRICE_LIST")
    private final Integer priceList;

    @Column("START_DATE")
    private final LocalDateTime startDate;

    @Column("END_DATE")
    private final LocalDateTime endDate;

    @Column("PRICE")
    private final Double price;

    @Column("CURR")
    private final String currency;

    @Column("PRIORITY")
    private final Integer priority;

}
