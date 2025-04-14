package com.swiss.codingchallange.asset_service.service;


import com.swiss.codingchallange.asset_service.client.PriceClient;
import com.swiss.codingchallange.asset_service.dto.AssetDTO;
import com.swiss.codingchallange.asset_service.model.Asset;
import com.swiss.codingchallange.asset_service.repository.AssetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class AssetService {

    @Autowired
    private AssetRepository assetRepository;

    @Autowired
    private PriceClient priceClient;

    public List<AssetDTO> getAssetsByWalletId(Long walletId) {
        return assetRepository.findByWalletId(walletId)
                .stream()
                .map(asset -> {
                    AssetDTO dto = new AssetDTO();
                    dto.setSymbol(asset.getSymbol());
                    dto.setQuantity(asset.getQuantity());
                    dto.setPrice(asset.getPrice());
                    return dto;
                }).collect(toList());
    }


    public void addAsset(Long walletId, AssetDTO dto) {
        Double verifiedPrice = priceClient.getCurrentPrice(dto.getSymbol());

        if (verifiedPrice == null) {
            throw new RuntimeException("Invalid token: " + dto.getSymbol());
        }

        Asset asset = Asset.builder()
                .walletId(walletId)
                .symbol(dto.getSymbol())
                .quantity(dto.getQuantity())
                .price(verifiedPrice)
                .build();

        assetRepository.save(asset);
    }

}
