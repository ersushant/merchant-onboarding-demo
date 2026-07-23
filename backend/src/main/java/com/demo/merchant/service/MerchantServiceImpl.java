package com.demo.merchant.service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.demo.merchant.dto.MerchantRequest;
import com.demo.merchant.dto.MerchantResponse;
import com.demo.merchant.entity.Merchant;
import com.demo.merchant.exception.MerchantNotFoundException;
import com.demo.merchant.repository.MerchantRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MerchantServiceImpl implements MerchantService {

    private static final Logger log = LoggerFactory.getLogger(MerchantServiceImpl.class);
    private final MerchantRepository repository;

    public MerchantServiceImpl(MerchantRepository repository) {
        this.repository = repository;
    }

    @Override
    public MerchantResponse createMerchant(MerchantRequest request) {

        Merchant merchant = new Merchant();

        merchant.setMerchantName(request.getMerchantName());
        merchant.setEmail(request.getEmail());
        merchant.setPhone(request.getPhone());
        merchant.setStatus("PENDING");

        Merchant saved = repository.save(merchant);

        return mapToResponse(saved);
    }

    @Override
    public List<MerchantResponse> getAllMerchants() {

        return repository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public MerchantResponse getMerchantById(Long id) {

        Merchant merchant = repository.findById(id)
                .orElseThrow(() -> new MerchantNotFoundException(id));

        return mapToResponse(merchant);
    }

    @Override
    public MerchantResponse updateMerchant(Long id, MerchantRequest request) {

        Merchant merchant = repository.findById(id)
                .orElseThrow(() -> new MerchantNotFoundException(id));

        merchant.setMerchantName(request.getMerchantName());
        merchant.setEmail(request.getEmail());
        merchant.setPhone(request.getPhone());

        Merchant updated = repository.save(merchant);

        return mapToResponse(updated);
    }

    @Override
    public void deleteMerchant(Long id) {

        repository.deleteById(id);

    }

    private MerchantResponse mapToResponse(Merchant merchant) {

        return new MerchantResponse(
                merchant.getId(),
                merchant.getMerchantName(),
                merchant.getEmail(),
                merchant.getPhone(),
                merchant.getStatus()
        );
    }
}