package com.swiss.codingchallange.asset_service.dto;


import lombok.Data;

@Data
public class AssetDTO {
    private String symbol;
    private double quantity;
    private double price;
}
