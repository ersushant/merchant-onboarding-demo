package com.demo.merchant.dto;

import com.demo.merchant.entity.MerchantStatus;
import jakarta.validation.constraints.NotNull;

public class UpdateMerchantStatusRequest {

    @NotNull(message = "Status is required")
    private MerchantStatus status;

    public UpdateMerchantStatusRequest() {
    }

    public MerchantStatus getStatus() {
        return status;
    }

    public void setStatus(MerchantStatus status) {
        this.status = status;
    }
}