package com.xis.prices.infrastructure.mapper;

import com.xis.prices.domain.model.Price;
import com.xis.prices.infrastructure.entity.PriceEntity;
import org.mapstruct.Mapper;

/**
 * Price infrastructure mapper
 *
 * @author XIS
 */
@Mapper
public interface PriceInfrastructureMapper {

    /**
     * Map to domain
     *
     * @param priceEntity Price entity
     * @return Price
     */
    Price toDomain(PriceEntity priceEntity);

}
