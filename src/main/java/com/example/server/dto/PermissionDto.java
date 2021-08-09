package com.example.server.dto;

import com.example.server.constant.AccessRight;
import lombok.Builder;

@Builder
public class PermissionDto {
    public Long userNo;
    public Long groupNo;
    public AccessRight accessRight;
}
