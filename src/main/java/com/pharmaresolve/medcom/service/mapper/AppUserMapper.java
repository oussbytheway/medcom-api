package com.pharmaresolve.medcom.service.mapper;

import com.pharmaresolve.medcom.domain.AppUser;
import com.pharmaresolve.medcom.domain.Pharmacy;
import com.pharmaresolve.medcom.service.dto.AppUserDTO;
import com.pharmaresolve.medcom.service.dto.PharmacyDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link AppUser} and its DTO {@link AppUserDTO}.
 */
@Mapper(componentModel = "spring")
public interface AppUserMapper extends EntityMapper<AppUserDTO, AppUser> {
    @Mapping(target = "pharmacy", source = "pharmacy", qualifiedByName = "pharmacyId")
    AppUserDTO toDto(AppUser s);

    @Named("pharmacyId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PharmacyDTO toDtoPharmacyId(Pharmacy pharmacy);
}
