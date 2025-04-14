package com.swiss.codingchallange.evaluation_service.dto;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EvaluationResponse {
    private double total;
    private String bestAsset;
    private double bestPerformance;
    private String worstAsset;
    private double worstPerformance;
}
