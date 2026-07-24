package com.demo.merchant.service;

import com.demo.merchant.dto.MerchantRequest;
import com.demo.merchant.dto.MerchantResponse;
import com.demo.merchant.dto.UpdateMerchantStatusRequest;
import com.demo.merchant.entity.MerchantStatus;
import com.demo.merchant.exception.DuplicateRequestException;
import com.demo.merchant.exception.MerchantNotFoundException;
import com.demo.merchant.repository.IdempotencyRepository;
import com.demo.merchant.repository.MerchantRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MerchantServiceImplTest {
    @Autowired private MerchantService merchantService;
    @Autowired private MerchantRepository merchantRepository;
    @Autowired private IdempotencyRepository idempotencyRepository;

    @Test
    void createsApplicationWithAnIdempotencyRecord() {
        MerchantResponse response = merchantService.createMerchant(request(), "key-1");

        assertNotNull(response.getId());
        assertEquals(MerchantStatus.APPLICATION_SUBMITTED, response.getStatus());
        assertTrue(idempotencyRepository.findByIdempotencyKey("key-1").isPresent());
    }

    @Test
    void repeatsSameKeyAndPayloadWithoutCreatingAnotherMerchant() {
        MerchantResponse first = merchantService.createMerchant(request(), "key-1");
        MerchantResponse replay = merchantService.createMerchant(request(), "key-1");

        assertEquals(first.getId(), replay.getId());
        assertEquals(1, merchantRepository.count());
    }

    @Test
    void rejectsReuseOfKeyWithDifferentPayload() {
        merchantService.createMerchant(request(), "key-1");
        MerchantRequest changed = new MerchantRequest("Different Store", "hello@abc.test", "9876543210");

        assertThrows(DuplicateRequestException.class, () -> merchantService.createMerchant(changed, "key-1"));
    }

    @Test
    void allowsOnlyValidStatusTransitions() {
        MerchantResponse created = merchantService.createMerchant(request(), "key-1");
        MerchantResponse reviewed = merchantService.updateMerchantStatus(created.getId(), status(MerchantStatus.UNDER_REVIEW));

        assertEquals(MerchantStatus.UNDER_REVIEW, reviewed.getStatus());
        assertThrows(IllegalArgumentException.class,
                () -> merchantService.updateMerchantStatus(created.getId(), status(MerchantStatus.SHIPPED)));
    }

    @Test
    void returnsNotFoundForUnknownMerchant() {
        assertThrows(MerchantNotFoundException.class, () -> merchantService.getMerchantById(99L));
    }

    private MerchantRequest request() {
        return new MerchantRequest("ABC Electronics", "hello@abc.test", "9876543210");
    }

    private UpdateMerchantStatusRequest status(MerchantStatus status) {
        UpdateMerchantStatusRequest request = new UpdateMerchantStatusRequest();
        request.setStatus(status);
        return request;
    }
}
