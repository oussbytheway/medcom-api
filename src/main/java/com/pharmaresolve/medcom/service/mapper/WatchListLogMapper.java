package com.pharmaresolve.medcom.service.mapper;

import com.pharmaresolve.medcom.domain.AppUser;
import com.pharmaresolve.medcom.domain.Pharmacy;
import com.pharmaresolve.medcom.domain.WatchListItem;
import com.pharmaresolve.medcom.domain.WatchListLog;
import com.pharmaresolve.medcom.service.dto.AppUserDTO;
import com.pharmaresolve.medcom.service.dto.PharmacyDTO;
import com.pharmaresolve.medcom.service.dto.WatchListItemDTO;
import com.pharmaresolve.medcom.service.dto.WatchListLogDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link WatchListLog} and its DTO {@link WatchListLogDTO}.
 */
@Mapper(componentModel = "spring")
public interface WatchListLogMapper extends EntityMapper<WatchListLogDTO, WatchListLog> {
    @Mapping(target = "watchListItem", source = "watchListItem", qualifiedByName = "watchListItemId")
    @Mapping(target = "appUser", source = "appUser", qualifiedByName = "appUserId")
    @Mapping(target = "pharmacy", source = "pharmacy", qualifiedByName = "pharmacyId")
    WatchListLogDTO toDto(WatchListLog s);

    @Named("watchListItemId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    WatchListItemDTO toDtoWatchListItemId(WatchListItem watchListItem);

    @Named("appUserId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AppUserDTO toDtoAppUserId(AppUser appUser);

    @Named("pharmacyId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PharmacyDTO toDtoPharmacyId(Pharmacy pharmacy);
}
