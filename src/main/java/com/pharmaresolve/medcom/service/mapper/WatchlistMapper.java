package com.pharmaresolve.medcom.service.mapper;

import com.pharmaresolve.medcom.domain.Pharmacy;
import com.pharmaresolve.medcom.domain.Watchlist;
import com.pharmaresolve.medcom.service.dto.PharmacyDTO;
import com.pharmaresolve.medcom.service.dto.WatchlistDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Watchlist} and its DTO {@link WatchlistDTO}.
 */
@Mapper(componentModel = "spring")
public interface WatchlistMapper extends EntityMapper<WatchlistDTO, Watchlist> {
    @Mapping(target = "pharmacy", source = "pharmacy", qualifiedByName = "pharmacyId")
    WatchlistDTO toDto(Watchlist s);

    @Named("pharmacyId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PharmacyDTO toDtoPharmacyId(Pharmacy pharmacy);
}
