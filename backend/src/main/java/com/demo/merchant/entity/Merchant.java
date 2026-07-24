package com.demo.merchant.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "merchants")
public class Merchant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String merchantName;

    private String email;

    private String phone;

    @Enumerated(EnumType.STRING)
    private MerchantStatus status;

    public Merchant() {
    }

    public Merchant(Long id,
                    String merchantName,
                    String email,
                    String phone,
                    MerchantStatus status) {
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