package com.swiss.codingchallange.wallet_service.dto;

import lombok.Data;

import java.util.List;
@Data
public class WalletDTO {
    private Long id;
    private String email;
    private List<AssetDTO> assets;
    private double totalValue;
}