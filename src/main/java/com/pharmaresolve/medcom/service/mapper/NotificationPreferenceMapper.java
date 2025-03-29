package com.pharmaresolve.medcom.service.mapper;

import com.pharmaresolve.medcom.domain.NotificationPreference;
import com.pharmaresolve.medcom.service.dto.NotificationPreferenceDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link NotificationPreference} and its DTO {@link NotificationPreferenceDTO}.
 */
@Mapper(componentModel = "spring")
public interface NotificationPreferenceMapper extends EntityMapper<NotificationPreferenceDTO, NotificationPreference> {}
