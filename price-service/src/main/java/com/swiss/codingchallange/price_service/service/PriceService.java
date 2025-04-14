package com.swiss.codingchallange.price_service.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.swiss.codingchallange.price_service.model.PriceHistory;
import com.swiss.codingchallange.price_service.repository.PriceHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PriceService {

    private final PriceHistoryRepository priceHistoryRepository;

    @Value("${coincap.api.key}") // Reads the API key from application.properties
    private String apiKey;

    @Value("${coincap.api.base.url}") // Reads the base URL from application.properties
    private String baseUrl;

    // Create WebClient instance dynamically based on the base URL
    private WebClient getWebClient() {
        return WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    public Double getCurrentPrice(String symbol) {
        try {
            // Use the dynamically created WebClient
            JsonNode json = getWebClient().get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/assets/{symbol}")
                            .queryParam("apiKey", apiKey) // Add API key dynamically
                            .build(symbol.toLowerCase())
                    )
                    .retrieve()
                    .bodyToMono(JsonNode.class)
                    .block();

            double price = json.get("data").get("priceUsd").asDouble();

            PriceHistory history = PriceHistory.builder()
                    .symbol(symbol)
                    .price(price)
                    .timestamp(LocalDateTime.now())
                    .build();

            priceHistoryRepository.save(history);
            return price;

        } catch (Exception e) {
            return null; // Not found or error
        }
    }

    public List<String> findDistinctSymbols() {
        return priceHistoryRepository.findDistinctSymbols();
    }
}