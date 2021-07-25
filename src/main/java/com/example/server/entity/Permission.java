package com.example.server.entity;

import com.example.server.constant.AccessRight;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "_PERMISSION")
@NoArgsConstructor
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long permissionNo;

    public Long userNo;

    public Long groupNo;

    public AccessRight accessRight;

    @Builder
    public Permission(Long permissionNo, Long userNo, Long groupNo, AccessRight accessRight) {
        this.permissionNo = permissionNo;
        this.userNo = userNo;
        this.groupNo = groupNo;
        this.accessRight = accessRight;
    }
}
