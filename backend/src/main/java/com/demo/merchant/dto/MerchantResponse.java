package com.demo.merchant.dto;

import com.demo.merchant.entity.MerchantStatus;

public class MerchantResponse {

    private Long id;
    private String merchantName;
    private String email;
    private String phone;
    private MerchantStatus status;

    public MerchantResponse() {
    }

    public MerchantResponse(Long id, String merchantName, String email,
                            String phone, MerchantStatus status) {
        this.id = id;
        this.merchantName = merchantName;
        this.email = email;
        this.phone = phone;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public MerchantStatus getStatus() {
        return status;
    }

    public void setStatus(MerchantStatus status) {
        this.status = status;
    }
}
