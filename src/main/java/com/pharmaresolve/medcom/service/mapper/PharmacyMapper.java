package com.pharmaresolve.medcom.service.mapper;

import com.pharmaresolve.medcom.domain.Pharmacy;
import com.pharmaresolve.medcom.domain.PharmacySubscription;
import com.pharmaresolve.medcom.domain.User;
import com.pharmaresolve.medcom.service.dto.PharmacyDTO;
import com.pharmaresolve.medcom.service.dto.PharmacySubscriptionDTO;
import com.pharmaresolve.medcom.service.dto.UserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Pharmacy} and its DTO {@link PharmacyDTO}.
 */
@Mapper(componentModel = "spring")
public interface PharmacyMapper extends EntityMapper<PharmacyDTO, Pharmacy> {
    @Mapping(target = "subscription", source = "subscription", qualifiedByName = "pharmacySubscriptionId")
    @Mapping(target = "admin", source = "admin", qualifiedByName = "userLogin")
    PharmacyDTO toDto(Pharmacy s);

    @Named("pharmacySubscriptionId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PharmacySubscriptionDTO toDtoPharmacySubscriptionId(PharmacySubscription pharmacySubscription);

    @Named("userLogin")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "login", source = "login")
    UserDTO toDtoUserLogin(User user);
}
