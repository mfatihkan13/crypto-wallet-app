package com.swiss.codingchallange.wallet_service;

import com.swiss.codingchallange.wallet_service.client.AssetClient;
import com.swiss.codingchallange.wallet_service.dto.AssetDTO;
import com.swiss.codingchallange.wallet_service.dto.WalletCreateRequest;
import com.swiss.codingchallange.wallet_service.dto.WalletDTO;
import com.swiss.codingchallange.wallet_service.model.User;
import com.swiss.codingchallange.wallet_service.model.Wallet;
import com.swiss.codingchallange.wallet_service.repository.UserRepository;
import com.swiss.codingchallange.wallet_service.repository.WalletRepository;
import com.swiss.codingchallange.wallet_service.service.WalletService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WalletServiceTest {

    @Mock
    UserRepository userRepository;
    @Mock
    WalletRepository walletRepository;
    @Mock
    AssetClient assetClient;

    @InjectMocks
    WalletService walletService;

    @Test
    void shouldCreateWallet() {
        WalletCreateRequest request = new WalletCreateRequest();
        request.setEmail("test@example.com");

        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.empty());
        when(userRepository.save(any())).thenAnswer(i -> i.getArguments()[0]);

        WalletDTO result = walletService.createWallet(request);

        assertEquals("test@example.com", result.getEmail());
    }

    @Test
    void shouldFetchWalletWithAssets() {
        User user = new User(1L, "john@doe.com", null);
        Wallet wallet = new Wallet(1L, user);
        user.setWallet(wallet);

        when(userRepository.findByEmail("john@doe.com")).thenReturn(Optional.of(user));
        when(assetClient.getAssets(1L)).thenReturn(List.of(
            new AssetDTO("btc", 1.0, 50000),
            new AssetDTO("eth", 2.0, 3000)
        ));

        WalletDTO result = walletService.getWalletByEmail("john@doe.com");

        assertEquals(2, result.getAssets().size());
        assertTrue(result.getTotalValue() > 0);
    }
}
