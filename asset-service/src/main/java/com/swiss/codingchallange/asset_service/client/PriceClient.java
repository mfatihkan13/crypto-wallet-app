package com.swiss.codingchallange.asset_service.client;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "price-service")
public interface PriceClient {

    @GetMapping("/price/{symbol}")
    Double getCurrentPrice(@PathVariable(name = "symbol") String symbol);
}
