package com.swiss.codingchallange.price_service.controller;

import com.swiss.codingchallange.price_service.service.PriceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/price")
@RequiredArgsConstructor
public class PriceController {

    private final PriceService priceService;

    @GetMapping("/{symbol}")
    public Double getPrice(@PathVariable("symbol") String symbol) {
        return priceService.getCurrentPrice(symbol);
    }
}
