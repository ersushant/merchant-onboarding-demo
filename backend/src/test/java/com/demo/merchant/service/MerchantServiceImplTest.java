package com.demo.merchant.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.demo.merchant.dto.MerchantRequest;
import com.demo.merchant.dto.MerchantResponse;
import com.demo.merchant.entity.Merchant;
import com.demo.merchant.exception.MerchantNotFoundException;
import com.demo.merchant.repository.MerchantRepository;

@ExtendWith(MockitoExtension.class)
class MerchantServiceImplTest {

    @Mock
    private MerchantRepository repository;

    @InjectMocks
    private MerchantServiceImpl merchantService;

    @Test
    void testCreateMerchant() {

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

        MerchantResponse response = merchantService.createMerchant(request);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("ABC Electronics", response.getMerchantName());
        assertEquals("abc@gmail.com", response.getEmail());
        assertEquals("9876543210", response.getPhone());
        assertEquals("PENDING", response.getStatus());

        verify(repository).save(any(Merchant.class));
    }

    @Test
    void testGetMerchantById() {

        Merchant merchant = new Merchant();
        merchant.setId(1L);
        merchant.setMerchantName("ABC Electronics");
        merchant.setEmail("abc@gmail.com");
        merchant.setPhone("9876543210");
        merchant.setStatus("PENDING");

        when(repository.findById(1L)).thenReturn(Optional.of(merchant));

        MerchantResponse response = merchantService.getMerchantById(1L);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("ABC Electronics", response.getMerchantName());
        assertEquals("abc@gmail.com", response.getEmail());
        assertEquals("9876543210", response.getPhone());
        assertEquals("PENDING", response.getStatus());

        verify(repository).findById(1L);
    }

    @Test
    void testGetMerchantById_NotFound() {

        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(
                MerchantNotFoundException.class,
                () -> merchantService.getMerchantById(1L)
        );

        verify(repository).findById(1L);
    }

    @Test
    void testGetAllMerchants() {

        Merchant merchant1 = new Merchant();
        merchant1.setId(1L);
        merchant1.setMerchantName("ABC Electronics");
        merchant1.setEmail("abc@gmail.com");
        merchant1.setPhone("9876543210");
        merchant1.setStatus("PENDING");

        Merchant merchant2 = new Merchant();
        merchant2.setId(2L);
        merchant2.setMerchantName("XYZ Store");
        merchant2.setEmail("xyz@gmail.com");
        merchant2.setPhone("9999999999");
        merchant2.setStatus("APPROVED");

        when(repository.findAll()).thenReturn(Arrays.asList(merchant1, merchant2));

        List<MerchantResponse> response = merchantService.getAllMerchants();

        assertNotNull(response);
        assertEquals(2, response.size());

        assertEquals("ABC Electronics", response.get(0).getMerchantName());
        assertEquals("XYZ Store", response.get(1).getMerchantName());

        verify(repository).findAll();
    }

    @Test
    void testUpdateMerchant() {

        MerchantRequest request = new MerchantRequest();
        request.setMerchantName("Updated Merchant");
        request.setEmail("updated@gmail.com");
        request.setPhone("9999999999");

        Merchant merchant = new Merchant();
        merchant.setId(1L);
        merchant.setMerchantName("ABC Electronics");
        merchant.setEmail("abc@gmail.com");
        merchant.setPhone("9876543210");
        merchant.setStatus("PENDING");

        Merchant updatedMerchant = new Merchant();
        updatedMerchant.setId(1L);
        updatedMerchant.setMerchantName("Updated Merchant");
        updatedMerchant.setEmail("updated@gmail.com");
        updatedMerchant.setPhone("9999999999");
        updatedMerchant.setStatus("PENDING");

        when(repository.findById(1L)).thenReturn(Optional.of(merchant));
        when(repository.save(any(Merchant.class))).thenReturn(updatedMerchant);

        MerchantResponse response = merchantService.updateMerchant(1L, request);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Updated Merchant", response.getMerchantName());
        assertEquals("updated@gmail.com", response.getEmail());
        assertEquals("9999999999", response.getPhone());
        assertEquals("PENDING", response.getStatus());

        verify(repository).findById(1L);
        verify(repository).save(any(Merchant.class));
    }

    @Test
    void testUpdateMerchant_NotFound() {

        MerchantRequest request = new MerchantRequest();
        request.setMerchantName("Updated Merchant");
        request.setEmail("updated@gmail.com");
        request.setPhone("9999999999");

        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(
                MerchantNotFoundException.class,
                () -> merchantService.updateMerchant(1L, request)
        );

        verify(repository).findById(1L);
    }

    @Test
    void testDeleteMerchant() {

        // Act
        merchantService.deleteMerchant(1L);

        // Assert
        verify(repository).deleteById(1L);
    }
}