package com.page.server.support;

import com.page.server.dto.TypeDto;
import com.page.server.entity.Type;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TypeConvert {
    Type from(TypeDto typeDto);
}
