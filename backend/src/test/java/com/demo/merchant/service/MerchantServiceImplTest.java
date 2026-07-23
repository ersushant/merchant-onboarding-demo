package com.demo.merchant.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.demo.merchant.dto.MerchantRequest;
import com.demo.merchant.dto.MerchantResponse;
import com.demo.merchant.entity.Merchant;
import com.demo.merchant.repository.MerchantRepository;

@ExtendWith(MockitoExtension.class)
class MerchantServiceImplTest {

    @Mock
    private MerchantRepository repository;

    @InjectMocks
    private MerchantServiceImpl merchantService;

    @Test
    void testCreateMerchant() {

        // Arrange
        MerchantRequest request = new MerchantRequest();
        request.setMerchantName("ABC Electronics");
        request.setEmail("abc@gmail.com");
        request.setPhone("9876543210");

        Merchant savedMerchant = new Merchant();
        savedMerchant.setId(1L);
        savedMerchant.setMerchantName("ABC Electronics");
        savedMerchant.setEmail("abc@gmail.com");
        savedMerchant.setPhone("9876543210");
        savedMerchant.setStatus("PENDING");

        when(repository.save(any(Merchant.class))).thenReturn(savedMerchant);

        // Act
        MerchantResponse response = merchantService.createMerchant(request);

        // Assert
        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("ABC Electronics", response.getMerchantName());
        assertEquals("abc@gmail.com", response.getEmail());
        assertEquals("9876543210", response.getPhone());
        assertEquals("PENDING", response.getStatus());

        verify(repository).save(any(Merchant.class));
    }
}