package com.xis.prices.infrastructure.repository.jpa;

import com.xis.prices.infrastructure.entity.PriceEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;

/**
 * Price reactive crud repository
 *
 * @author XIS
 */
@Repository
public interface PriceReactiveCrudRepository extends ReactiveCrudRepository<PriceEntity, Long> {

    /**
     * Search
     *
     * @param applicationDate Application date
     * @param productId       Product ID
     * @param brandId         Brand ID
     * @return Price entity
     */
    @Query("""
            SELECT p.brand_id, p.product_id, p.price_list, p.start_date, p.end_date, p.price, p.priority
            FROM Prices p
            WHERE (p.start_date < ?1 and p.end_date > ?1) and p.product_id = ?2 and p.brand_id = ?3
            """)
    Flux<PriceEntity> search(final LocalDateTime applicationDate, final Long productId, final Integer brandId);

}
