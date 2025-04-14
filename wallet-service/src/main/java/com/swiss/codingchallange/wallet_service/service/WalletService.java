package com.swiss.codingchallange.wallet_service.service;
import com.swiss.codingchallange.wallet_service.client.AssetClient;
import com.swiss.codingchallange.wallet_service.dto.AssetDTO;
import com.swiss.codingchallange.wallet_service.dto.WalletCreateRequest;
import com.swiss.codingchallange.wallet_service.dto.WalletDTO;
import com.swiss.codingchallange.wallet_service.model.User;
import com.swiss.codingchallange.wallet_service.model.Wallet;
import com.swiss.codingchallange.wallet_service.repository.UserRepository;
import com.swiss.codingchallange.wallet_service.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WalletService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private AssetClient assetClient;

    public WalletDTO createWallet(WalletCreateRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Wallet already exists for email: " + request.getEmail());
        }

        User user = new User();
        user.setEmail(request.getEmail());

        Wallet wallet = new Wallet();
        wallet.setUser(user);
        user.setWallet(wallet);

        userRepository.save(user);

        return buildWalletDTO(wallet, List.of());
    }

    public WalletDTO getWalletByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Wallet wallet = user.getWallet();
        List<AssetDTO> assets = assetClient.getAssets(wallet.getId());

        return buildWalletDTO(wallet, assets);
    }

    private WalletDTO buildWalletDTO(Wallet wallet, List<AssetDTO> assets) {
        double total = assets.stream()
                .mapToDouble(a -> a.getPrice() * a.getQuantity())
                .sum();

        WalletDTO dto = new WalletDTO();
        dto.setId(wallet.getId());
        dto.setEmail(wallet.getUser().getEmail());
        dto.setAssets(assets);
        dto.setTotalValue(total);
        return dto;
    }
}
