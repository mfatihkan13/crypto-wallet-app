package com.swiss.codingchallange.wallet_service.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class WalletCreateRequest {
    @NotNull
    private String email;
}