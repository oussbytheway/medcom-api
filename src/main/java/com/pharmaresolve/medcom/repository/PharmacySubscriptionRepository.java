package com.pharmaresolve.medcom.repository;

import com.pharmaresolve.medcom.domain.PharmacySubscription;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the PharmacySubscription entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PharmacySubscriptionRepository extends JpaRepository<PharmacySubscription, Long> {}
