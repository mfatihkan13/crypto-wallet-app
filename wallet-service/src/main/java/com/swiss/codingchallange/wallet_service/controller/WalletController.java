package com.swiss.codingchallange.wallet_service.controller;

import com.swiss.codingchallange.wallet_service.dto.WalletCreateRequest;
import com.swiss.codingchallange.wallet_service.dto.WalletDTO;
import com.swiss.codingchallange.wallet_service.service.WalletService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wallet")
public class WalletController {

    @Autowired
    private WalletService walletService;

    @PostMapping("/create")
    public ResponseEntity<WalletDTO> create(@RequestBody @Valid WalletCreateRequest request) {
        return ResponseEntity.ok(walletService.createWallet(request));
    }

    @GetMapping("/{email}")
    public ResponseEntity<WalletDTO> get(@PathVariable("email") String email) {
        return ResponseEntity.ok(walletService.getWalletByEmail(email));
    }
}