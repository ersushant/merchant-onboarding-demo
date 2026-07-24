package com.demo.merchant.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "idempotency_records")
public class IdempotencyRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "idempotency_key", nullable = false, unique = true)
    private String idempotencyKey;

    @Column(name = "request_hash", nullable = false)
    private String requestHash;

    @Column(name = "merchant_id", nullable = false)
    private Long merchantId;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public IdempotencyRecord() {
    }

    public IdempotencyRecord(String idempotencyKey,
                             String requestHash,
                             Long merchantId,
                             LocalDateTime createdAt) {
        this.idempotencyKey = idempotencyKey;
        this.requestHash = requestHash;
        this.merchantId = merchantId;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public String getIdempotencyKey() {
        return idempotencyKey;
    }

    public void setIdempotencyKey(String idempotencyKey) {
        this.idempotencyKey = idempotencyKey;
    }

    public String getRequestHash() {
        return requestHash;
    }

    public void setRequestHash(String requestHash) {
        this.requestHash = requestHash;
    }

    public Long getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Long merchantId) {
        this.merchantId = merchantId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}