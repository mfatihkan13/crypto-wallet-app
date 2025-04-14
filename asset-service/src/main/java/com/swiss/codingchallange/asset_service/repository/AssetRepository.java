package com.swiss.codingchallange.asset_service.repository;


import com.swiss.codingchallange.asset_service.model.Asset;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssetRepository extends JpaRepository<Asset, Long> {
    List<Asset> findByWalletId(Long walletId);
}
