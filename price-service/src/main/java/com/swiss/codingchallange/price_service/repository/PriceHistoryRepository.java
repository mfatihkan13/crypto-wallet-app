package com.swiss.codingchallange.price_service.repository;


import com.swiss.codingchallange.price_service.model.PriceHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PriceHistoryRepository extends JpaRepository<PriceHistory, Long> {
    Optional<PriceHistory> findTopBySymbolOrderByTimestampDesc(String symbol);

    @Query("SELECT DISTINCT p.symbol FROM PriceHistory p")
    List<String> findDistinctSymbols();

}
