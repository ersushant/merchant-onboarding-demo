package com.demo.merchant.service;

import com.demo.merchant.dto.MerchantRequest;
import com.demo.merchant.dto.MerchantResponse;
import com.demo.merchant.dto.UpdateMerchantStatusRequest;
import com.demo.merchant.entity.IdempotencyRecord;
import com.demo.merchant.entity.Merchant;
import com.demo.merchant.entity.MerchantStatus;
import com.demo.merchant.exception.DuplicateRequestException;
import com.demo.merchant.exception.MerchantNotFoundException;
import com.demo.merchant.repository.IdempotencyRepository;
import com.demo.merchant.repository.MerchantRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.HexFormat;
import java.util.List;

@Service
public class MerchantServiceImpl implements MerchantService {

    private final MerchantRepository merchantRepository;
    private final IdempotencyRepository idempotencyRepository;
    private final ObjectMapper objectMapper;

    public MerchantServiceImpl(MerchantRepository merchantRepository,
                               IdempotencyRepository idempotencyRepository,
                               ObjectMapper objectMapper) {
        this.merchantRepository = merchantRepository;
        this.idempotencyRepository = idempotencyRepository;
        this.objectMapper = objectMapper;
    }

    /**
     * Creates one merchant for a client-supplied key. Repeating the same key
     * and payload returns the original merchant; a changed payload is rejected.
     */
    @Override
    @Transactional
    public MerchantResponse createMerchant(MerchantRequest request, String idempotencyKey) {
        String requestHash = hash(request);

        IdempotencyRecord existing = idempotencyRepository
                .findWithLockByIdempotencyKey(idempotencyKey)
                .orElse(null);
        if (existing != null) {
            if (!existing.getRequestHash().equals(requestHash)) {
                throw new DuplicateRequestException(
                        "Idempotency-Key was already used with a different request payload.");
            }
            return merchantRepository.findById(existing.getMerchantId())
                    .map(this::toResponse)
                    .orElseThrow(() -> new IllegalStateException("Idempotency record refers to a missing merchant."));
        }

        Merchant savedMerchant = merchantRepository.save(newMerchant(request));
        try {
            idempotencyRepository.saveAndFlush(new IdempotencyRecord(
                    idempotencyKey, requestHash, savedMerchant.getId(), LocalDateTime.now()));
        } catch (DataIntegrityViolationException ex) {
            // The database uniqueness constraint also protects against a race
            // between two requests that arrive with the same key.
            throw new DuplicateRequestException("Idempotency-Key is currently being processed; retry the request.");
        }
        return toResponse(savedMerchant);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MerchantResponse> getAllMerchants() {
        return merchantRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public MerchantResponse getMerchantById(Long id) {
        return toResponse(findMerchant(id));
    }

    @Override
    @Transactional
    public MerchantResponse updateMerchant(Long id, MerchantRequest request) {
        Merchant merchant = findMerchant(id);
        merchant.setMerchantName(request.getMerchantName());
        merchant.setEmail(request.getEmail());
        merchant.setPhone(request.getPhone());
        return toResponse(merchantRepository.save(merchant));
    }

    @Override
    @Transactional
    public MerchantResponse updateMerchantStatus(Long id, UpdateMerchantStatusRequest request) {
        Merchant merchant = findMerchant(id);
        MerchantStatus next = request.getStatus();
        if (!isAllowedTransition(merchant.getStatus(), next)) {
            throw new IllegalArgumentException("Cannot transition merchant from "
                    + merchant.getStatus() + " to " + next + ".");
        }
        merchant.setStatus(next);
        return toResponse(merchantRepository.save(merchant));
    }

    @Override
    @Transactional
    public void deleteMerchant(Long id) {
        merchantRepository.delete(findMerchant(id));
    }

    private Merchant findMerchant(Long id) {
        return merchantRepository.findById(id).orElseThrow(() -> new MerchantNotFoundException(id));
    }

    private Merchant newMerchant(MerchantRequest request) {
        Merchant merchant = new Merchant();
        merchant.setMerchantName(request.getMerchantName());
        merchant.setEmail(request.getEmail());
        merchant.setPhone(request.getPhone());
        merchant.setStatus(MerchantStatus.APPLICATION_SUBMITTED);
        return merchant;
    }

    private boolean isAllowedTransition(MerchantStatus current, MerchantStatus next) {
        return switch (current) {
            case APPLICATION_SUBMITTED -> next == MerchantStatus.UNDER_REVIEW;
            case UNDER_REVIEW -> next == MerchantStatus.APPROVED || next == MerchantStatus.REJECTED;
            case APPROVED -> next == MerchantStatus.SHIPPED;
            case SHIPPED, REJECTED -> false;
        };
    }

    private String hash(MerchantRequest request) {
        try {
            String json = objectMapper.writeValueAsString(request);
            return HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256")
                    .digest(json.getBytes(StandardCharsets.UTF_8)));
        } catch (JsonProcessingException | NoSuchAlgorithmException ex) {
            throw new IllegalStateException("Unable to process idempotent request.", ex);
        }
    }

    private MerchantResponse toResponse(Merchant merchant) {
        return new MerchantResponse(merchant.getId(), merchant.getMerchantName(), merchant.getEmail(),
                merchant.getPhone(), merchant.getStatus());
    }
}
