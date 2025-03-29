package com.pharmaresolve.medcom.service.mapper;

import com.pharmaresolve.medcom.domain.Product;
import com.pharmaresolve.medcom.domain.WatchList;
import com.pharmaresolve.medcom.domain.WatchListItem;
import com.pharmaresolve.medcom.service.dto.ProductDTO;
import com.pharmaresolve.medcom.service.dto.WatchListDTO;
import com.pharmaresolve.medcom.service.dto.WatchListItemDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link WatchListItem} and its DTO {@link WatchListItemDTO}.
 */
@Mapper(componentModel = "spring")
public interface WatchListItemMapper extends EntityMapper<WatchListItemDTO, WatchListItem> {
    @Mapping(target = "watchList", source = "watchList", qualifiedByName = "watchListId")
    @Mapping(target = "product", source = "product", qualifiedByName = "productId")
    WatchListItemDTO toDto(WatchListItem s);

    @Named("watchListId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    WatchListDTO toDtoWatchListId(WatchList watchList);

    @Named("productId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ProductDTO toDtoProductId(Product product);
}
