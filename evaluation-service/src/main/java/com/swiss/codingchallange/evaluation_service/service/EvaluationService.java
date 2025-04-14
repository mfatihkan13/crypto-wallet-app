package com.swiss.codingchallange.evaluation_service.service;

import com.swiss.codingchallange.evaluation_service.client.PriceClient;
import com.swiss.codingchallange.evaluation_service.dto.AssetValuationDTO;
import com.swiss.codingchallange.evaluation_service.dto.EvaluationRequest;
import com.swiss.codingchallange.evaluation_service.dto.EvaluationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EvaluationService {

    private final PriceClient priceClient;

    public EvaluationResponse evaluateWallet(EvaluationRequest request) {
        double total = 0;
        String bestAsset = null;
        String worstAsset = null;
        double bestPerformance = Double.NEGATIVE_INFINITY;
        double worstPerformance = Double.POSITIVE_INFINITY;

        for (AssetValuationDTO asset : request.getAssets()) {
            double currentPrice = priceClient.getCurrentPrice(asset.getSymbol());
            double currentValue = currentPrice * asset.getQuantity();
            double pastValue = asset.getValue();

            double performance = ((currentValue - pastValue) / pastValue) * 100;

            total += currentValue;

            if (performance > bestPerformance) {
                bestPerformance = performance;
                bestAsset = asset.getSymbol();
            }

            if (performance < worstPerformance) {
                worstPerformance = performance;
                worstAsset = asset.getSymbol();
            }
        }

        return EvaluationResponse.builder()
                .total(total)
                .bestAsset(bestAsset)
                .bestPerformance(round(bestPerformance))
                .worstAsset(worstAsset)
                .worstPerformance(round(worstPerformance))
                .build();
    }

    private double round(double val) {
        return Math.round(val * 100.0) / 100.0;
    }
}
