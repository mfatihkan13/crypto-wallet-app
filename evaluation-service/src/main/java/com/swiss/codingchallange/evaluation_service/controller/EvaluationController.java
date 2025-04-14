package com.swiss.codingchallange.evaluation_service.controller;

import com.swiss.codingchallange.evaluation_service.dto.EvaluationRequest;
import com.swiss.codingchallange.evaluation_service.dto.EvaluationResponse;
import com.swiss.codingchallange.evaluation_service.service.EvaluationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/evaluate")
@RequiredArgsConstructor
public class EvaluationController {

    private final EvaluationService evaluationService;

    @PostMapping
    public EvaluationResponse evaluate(@RequestBody EvaluationRequest request) {
        return evaluationService.evaluateWallet(request);
    }
}
