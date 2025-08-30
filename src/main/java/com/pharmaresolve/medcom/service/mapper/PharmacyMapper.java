package com.pharmaresolve.medcom.service.mapper;

import com.pharmaresolve.medcom.domain.Pharmacy;
import com.pharmaresolve.medcom.service.dto.PharmacyDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Pharmacy} and its DTO {@link PharmacyDTO}.
 */
@Mapper(componentModel = "spring")
public interface PharmacyMapper extends EntityMapper<PharmacyDTO, Pharmacy> {}
