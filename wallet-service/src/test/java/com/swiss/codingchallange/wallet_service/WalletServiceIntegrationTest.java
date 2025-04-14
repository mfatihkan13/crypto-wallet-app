package com.swiss.codingchallange.wallet_service;

import com.swiss.codingchallange.wallet_service.client.AssetClient;
import com.swiss.codingchallange.wallet_service.dto.WalletCreateRequest;
import com.swiss.codingchallange.wallet_service.dto.WalletDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class WalletServiceIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private AssetClient assetClient;

    @BeforeEach
    void setUpMock() {
        when(assetClient.getAssets(anyLong())).thenReturn(List.of());
    }


    @Test
    void testCreateAndFetchWallet() {
        String email = "integration@test.com";

        // Create Wallet
        WalletCreateRequest request = new WalletCreateRequest();
        request.setEmail(email);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<WalletCreateRequest> entity = new HttpEntity<>(request, headers);

        ResponseEntity<WalletDTO> createResponse = restTemplate.postForEntity(
                "http://localhost:" + port + "/wallet/create", entity, WalletDTO.class);

        assertEquals(HttpStatus.OK, createResponse.getStatusCode());
        assertNotNull(createResponse.getBody());
        assertEquals(email, createResponse.getBody().getEmail());

        // Fetch Wallet
        ResponseEntity<WalletDTO> getResponse = restTemplate.getForEntity(
                "http://localhost:" + port + "/wallet/" + email, WalletDTO.class);

        assertEquals(HttpStatus.OK, getResponse.getStatusCode());
        assertNotNull(getResponse.getBody());
        assertEquals(email, getResponse.getBody().getEmail());
    }
}
