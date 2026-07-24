package com.demo.merchant.service;

import com.demo.merchant.dto.MerchantRequest;
import com.demo.merchant.dto.MerchantResponse;
import com.demo.merchant.dto.UpdateMerchantStatusRequest;

import java.util.List;

public interface MerchantService {

    MerchantResponse createMerchant(MerchantRequest request,
                                    String idempotencyKey);

    List<MerchantResponse> getAllMerchants();

    MerchantResponse getMerchantById(Long id);

    MerchantResponse updateMerchant(Long id,
                                    MerchantRequest request);

    MerchantResponse updateMerchantStatus(Long id,
                                          UpdateMerchantStatusRequest request);

    void deleteMerchant(Long id);
}
