package com.swiss.codingchallange.asset_service.controller;

import com.swiss.codingchallange.asset_service.dto.AssetDTO;
import com.swiss.codingchallange.asset_service.service.AssetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/asset")
public class AssetController {

    @Autowired
    private AssetService assetService;

    @GetMapping("/by-wallet/{walletId}")
    public List<AssetDTO> getAssets(@PathVariable("walletId") Long walletId) {
        return assetService.getAssetsByWalletId(walletId);
    }

    @PostMapping("/add/{walletId}")
    public void addAsset(@PathVariable("walletId") Long walletId, @RequestBody AssetDTO dto) {
        assetService.addAsset(walletId, dto);
    }
}
