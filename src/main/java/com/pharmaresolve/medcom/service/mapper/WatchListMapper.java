package com.pharmaresolve.medcom.service.mapper;

import com.pharmaresolve.medcom.domain.Pharmacy;
import com.pharmaresolve.medcom.domain.WatchList;
import com.pharmaresolve.medcom.service.dto.PharmacyDTO;
import com.pharmaresolve.medcom.service.dto.WatchListDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link WatchList} and its DTO {@link WatchListDTO}.
 */
@Mapper(componentModel = "spring")
public interface WatchListMapper extends EntityMapper<WatchListDTO, WatchList> {
    @Mapping(target = "pharmacy", source = "pharmacy", qualifiedByName = "pharmacyId")
    WatchListDTO toDto(WatchList s);

    @Named("pharmacyId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PharmacyDTO toDtoPharmacyId(Pharmacy pharmacy);
}
