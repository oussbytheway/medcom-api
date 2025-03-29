package com.pharmaresolve.medcom.service.mapper;

import com.pharmaresolve.medcom.domain.Alert;
import com.pharmaresolve.medcom.domain.WatchListItem;
import com.pharmaresolve.medcom.service.dto.AlertDTO;
import com.pharmaresolve.medcom.service.dto.WatchListItemDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Alert} and its DTO {@link AlertDTO}.
 */
@Mapper(componentModel = "spring")
public interface AlertMapper extends EntityMapper<AlertDTO, Alert> {
    @Mapping(target = "watchListItem", source = "watchListItem", qualifiedByName = "watchListItemId")
    AlertDTO toDto(Alert s);

    @Named("watchListItemId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    WatchListItemDTO toDtoWatchListItemId(WatchListItem watchListItem);
}
