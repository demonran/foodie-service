package com.foodie.portal.merchant.repository;

import com.foodie.portal.merchant.MerchantStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MerchantJpaRepository extends JpaRepository<MerchantEntity, String> {

    Page<MerchantEntity> findByStatus(MerchantStatus status, Pageable pageable);
}
