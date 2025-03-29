package com.pharmaresolve.medcom.service.mapper;

import com.pharmaresolve.medcom.domain.AppConnectionLog;
import com.pharmaresolve.medcom.domain.AppUser;
import com.pharmaresolve.medcom.domain.Pharmacy;
import com.pharmaresolve.medcom.service.dto.AppConnectionLogDTO;
import com.pharmaresolve.medcom.service.dto.AppUserDTO;
import com.pharmaresolve.medcom.service.dto.PharmacyDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link AppConnectionLog} and its DTO {@link AppConnectionLogDTO}.
 */
@Mapper(componentModel = "spring")
public interface AppConnectionLogMapper extends EntityMapper<AppConnectionLogDTO, AppConnectionLog> {
    @Mapping(target = "appUser", source = "appUser", qualifiedByName = "appUserId")
    @Mapping(target = "pharmacy", source = "pharmacy", qualifiedByName = "pharmacyId")
    AppConnectionLogDTO toDto(AppConnectionLog s);

    @Named("appUserId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AppUserDTO toDtoAppUserId(AppUser appUser);

    @Named("pharmacyId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PharmacyDTO toDtoPharmacyId(Pharmacy pharmacy);
}
