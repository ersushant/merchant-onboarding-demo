package com.demo.merchant.entity;

/**
 * The lifecycle is deliberately explicit: an application is reviewed, then
 * either rejected or approved for shipment.  SHIPPED and REJECTED are final.
 */
public enum MerchantStatus {
    APPLICATION_SUBMITTED,
    UNDER_REVIEW,
    APPROVED,
    SHIPPED,
    REJECTED
}
