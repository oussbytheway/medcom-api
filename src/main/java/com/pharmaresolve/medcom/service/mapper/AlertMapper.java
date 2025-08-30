package com.pharmaresolve.medcom.service.mapper;

import com.pharmaresolve.medcom.domain.Alert;
import com.pharmaresolve.medcom.domain.WatchlistItem;
import com.pharmaresolve.medcom.service.dto.AlertDTO;
import com.pharmaresolve.medcom.service.dto.WatchlistItemDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Alert} and its DTO {@link AlertDTO}.
 */
@Mapper(componentModel = "spring")
public interface AlertMapper extends EntityMapper<AlertDTO, Alert> {
    @Mapping(target = "watchlistItem", source = "watchlistItem", qualifiedByName = "watchlistItemId")
    AlertDTO toDto(Alert s);

    @Named("watchlistItemId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    WatchlistItemDTO toDtoWatchlistItemId(WatchlistItem watchlistItem);
}
