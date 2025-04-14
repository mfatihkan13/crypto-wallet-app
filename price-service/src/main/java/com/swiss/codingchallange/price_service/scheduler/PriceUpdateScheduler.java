package com.swiss.codingchallange.price_service.scheduler;


import com.fasterxml.jackson.databind.JsonNode;
import com.swiss.codingchallange.price_service.model.PriceHistory;
import com.swiss.codingchallange.price_service.repository.PriceHistoryRepository;
import com.swiss.codingchallange.price_service.service.PriceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
@RequiredArgsConstructor
@Slf4j
public class PriceUpdateScheduler {

    private final PriceService priceService;

    private final ExecutorService executor = Executors.newFixedThreadPool(3);

    @Scheduled(fixedDelayString = "${price.update-interval:60000}") // Default 1 min
    public void updatePrices() {
        List<String> symbols = priceService.findDistinctSymbols();

        log.info("Updating prices for {} symbols", symbols.size());

                List<CompletableFuture<Void>> tasks = symbols.stream()
                        .map(symbol -> CompletableFuture.runAsync(() -> updatePrice(symbol), executor))
                        .toList();

        CompletableFuture.allOf(tasks.toArray(new CompletableFuture[0])).join();
    }

    private void updatePrice(String symbol) {
        try {
            Double currentPrice = priceService.getCurrentPrice(symbol);
            log.info("Updated price for {} = {}", symbol, currentPrice);
        } catch (Exception e) {
            log.warn("Failed to update price for {}: {}", symbol, e.getMessage());
        }
    }
}
