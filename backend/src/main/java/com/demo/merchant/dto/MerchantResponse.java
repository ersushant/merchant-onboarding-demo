package com.demo.merchant.dto;

public class MerchantResponse {

    private Long id;
    private String merchantName;
    private String email;
    private String phone;
    private String status;

    public MerchantResponse() {
    }

    public MerchantResponse(Long id, String merchantName, String email,
                            String phone, String status) {
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}