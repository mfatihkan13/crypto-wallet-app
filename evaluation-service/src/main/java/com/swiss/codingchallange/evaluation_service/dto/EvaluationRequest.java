package com.swiss.codingchallange.evaluation_service.dto;


import lombok.Data;

import java.util.List;

@Data
public class EvaluationRequest {
    private List<AssetValuationDTO> assets;
}