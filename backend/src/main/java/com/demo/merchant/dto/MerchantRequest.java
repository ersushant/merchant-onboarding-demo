package com.demo.merchant.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class MerchantRequest {

    @NotBlank(message = "Merchant name is required")
    private String merchantName;

    @Email(message = "Invalid email")
    private String email;

    @NotBlank(message = "Phone is required")
    private String phone;

    public MerchantRequest() {
    }

    public MerchantRequest(String merchantName, String email, String phone) {
        this.merchantName = merchantName;
        this.email = email;
        this.phone = phone;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}