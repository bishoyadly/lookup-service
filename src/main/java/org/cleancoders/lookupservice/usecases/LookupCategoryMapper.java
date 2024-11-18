package org.cleancoders.lookupservice.usecases;

import org.cleancoders.lookupservice.entities.LookupCategory;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface LookupCategoryMapper {
    LookupCategory requestToEntity(CategoryRequest request);
}
