package com.swiss.codingchallange.wallet_service.client;

import com.swiss.codingchallange.wallet_service.dto.AssetDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "asset-service")
public interface AssetClient {
    @GetMapping("/asset/by-wallet/{walletId}")
    List<AssetDTO> getAssets(@PathVariable(name = "walletId") Long walletId);
}