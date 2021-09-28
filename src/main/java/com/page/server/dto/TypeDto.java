package com.page.server.dto;

import com.page.server.entity.Type;

public class TypeDto {
    public Long typeNo;

    public String typeName;

    public Type.DataType dataType;

    public String defaultValue;

    public Boolean required = false;

    public Boolean deleted = false;
}
