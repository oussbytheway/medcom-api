package com.pharmaresolve.medcom.service.mapper;

import com.pharmaresolve.medcom.domain.Alert;
import com.pharmaresolve.medcom.domain.AppUser;
import com.pharmaresolve.medcom.domain.Notification;
import com.pharmaresolve.medcom.service.dto.AlertDTO;
import com.pharmaresolve.medcom.service.dto.AppUserDTO;
import com.pharmaresolve.medcom.service.dto.NotificationDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Notification} and its DTO {@link NotificationDTO}.
 */
@Mapper(componentModel = "spring")
public interface NotificationMapper extends EntityMapper<NotificationDTO, Notification> {
    @Mapping(target = "alert", source = "alert", qualifiedByName = "alertId")
    @Mapping(target = "appUser", source = "appUser", qualifiedByName = "appUserId")
    NotificationDTO toDto(Notification s);

    @Named("alertId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AlertDTO toDtoAlertId(Alert alert);

    @Named("appUserId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AppUserDTO toDtoAppUserId(AppUser appUser);
}
