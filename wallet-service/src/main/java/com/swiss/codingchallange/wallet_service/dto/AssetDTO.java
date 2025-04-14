package com.swiss.codingchallange.wallet_service.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
public class AssetDTO {
    private String symbol;
    private double quantity;
    private double price;

    public AssetDTO(String symbol, double quantity, int price) {
        this.symbol = symbol;
        this.quantity = quantity;
        this.price = price;
    }
}