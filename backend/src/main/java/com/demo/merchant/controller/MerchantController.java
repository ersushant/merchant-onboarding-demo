package com.demo.merchant.controller;

import com.demo.merchant.dto.MerchantRequest;
import com.demo.merchant.dto.MerchantResponse;
import com.demo.merchant.dto.UpdateMerchantStatusRequest;
import com.demo.merchant.response.ApiResponse;
import com.demo.merchant.service.MerchantService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@RestController
@RequestMapping("/api/merchants")
@Validated
public class MerchantController {

    private final MerchantService service;

    public MerchantController(MerchantService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<MerchantResponse> createMerchant(
        @RequestHeader("Idempotency-Key") @NotBlank(message = "Idempotency-Key is required") String idempotencyKey,
        @Valid @RequestBody MerchantRequest request) {

    MerchantResponse response =
            service.createMerchant(request, idempotencyKey);

    return ResponseEntity.status(HttpStatus.CREATED).body(response);
}

    @GetMapping
    public ApiResponse<List<MerchantResponse>> getAllMerchants() {

        List<MerchantResponse> merchants = service.getAllMerchants();

        return new ApiResponse<>(
                true,
                "Merchants retrieved successfully",
                merchants
        );
    }

    @GetMapping("/{id}")
    public ApiResponse<MerchantResponse> getMerchantById(@PathVariable Long id) {

        MerchantResponse response = service.getMerchantById(id);

        return new ApiResponse<>(
                true,
                "Merchant retrieved successfully",
                response
        );
    }

    @PutMapping("/{id}")
    public ApiResponse<MerchantResponse> updateMerchant(
            @PathVariable Long id,
            @Valid @RequestBody MerchantRequest request) {

        MerchantResponse response = service.updateMerchant(id, request);

        return new ApiResponse<>(
                true,
                "Merchant updated successfully",
                response
        );
    }

    @PatchMapping("/{id}/status")
    public ApiResponse<MerchantResponse> updateMerchantStatus(
            @PathVariable Long id,
            @Valid @RequestBody UpdateMerchantStatusRequest request) {

        MerchantResponse response = service.updateMerchantStatus(id, request);

        return new ApiResponse<>(
                true,
                "Merchant status updated successfully",
                response
        );
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteMerchant(@PathVariable Long id) {

        service.deleteMerchant(id);

        return new ApiResponse<>(
                true,
                "Merchant deleted successfully",
                "Merchant with ID " + id + " has been deleted."
        );
    }
}
