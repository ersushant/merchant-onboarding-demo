package com.demo.merchant.service;

import com.demo.merchant.dto.MerchantRequest;
import com.demo.merchant.dto.MerchantResponse;

import java.util.List;
import com.demo.merchant.exception.MerchantNotFoundException;

public interface MerchantService {

    MerchantResponse createMerchant(MerchantRequest request);

    List<MerchantResponse> getAllMerchants();

    MerchantResponse getMerchantById(Long id);

    MerchantResponse updateMerchant(Long id, MerchantRequest request);

    void deleteMerchant(Long id);

}