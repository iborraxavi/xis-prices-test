package com.xis.prices.api.mapper;

import com.xis.prices.domain.model.Price;
import com.xis.prices.domain.model.PriceRequest;
import com.xis.prices.pricesapi.dto.PricesSearchResponse;
import org.mapstruct.Mapper;

import java.util.Date;

/**
 * Prices API mapper
 *
 * @author XIS
 */
@Mapper
public interface PricesApiMapper {

    /**
     * Map to domain price request
     *
     * @param applicationDate Application date
     * @param productId       Product id
     * @param brandId         Brand id
     * @return Domain price request
     */
    PriceRequest toDomainRequest(Date applicationDate, Long productId, Integer brandId);

    /**
     * Map tp prices search response
     *
     * @param price Price
     * @return Prices search response
     */
    PricesSearchResponse toPricesSearchResponse(Price price);

}
