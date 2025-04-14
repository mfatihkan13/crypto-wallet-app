package com.swiss.codingchallange.evaluation_service.dto;


import lombok.Data;

@Data
public class AssetValuationDTO {
    private String symbol;
    private double quantity;
    private double value; // past total value = price_at_that_time × quantity
}
