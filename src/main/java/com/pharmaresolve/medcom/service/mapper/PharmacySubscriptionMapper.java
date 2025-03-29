package com.pharmaresolve.medcom.service.mapper;

import com.pharmaresolve.medcom.domain.PharmacySubscription;
import com.pharmaresolve.medcom.service.dto.PharmacySubscriptionDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PharmacySubscription} and its DTO {@link PharmacySubscriptionDTO}.
 */
@Mapper(componentModel = "spring")
public interface PharmacySubscriptionMapper extends EntityMapper<PharmacySubscriptionDTO, PharmacySubscription> {}
