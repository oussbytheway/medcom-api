package com.pharmaresolve.medcom.service.mapper;

import com.pharmaresolve.medcom.domain.Product;
import com.pharmaresolve.medcom.service.dto.ProductDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Product} and its DTO {@link ProductDTO}.
 */
@Mapper(componentModel = "spring")
public interface ProductMapper extends EntityMapper<ProductDTO, Product> {}
