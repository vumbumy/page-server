package com.example.server.entity;

import com.example.server.constant.AccessRight;
import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "_PERMISSION")
@NoArgsConstructor
public class Permission implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long permissionNo;

    @Column
    public Long userNo;

    @Column
    public Long groupNo;

    @NotNull
    @Column
    public AccessRight accessRight;

    @Builder
    public Permission(Long permissionNo, Long userNo, Long groupNo, AccessRight accessRight) {
        this.permissionNo = permissionNo;
        this.userNo = userNo;
        this.groupNo = groupNo;
        this.accessRight = accessRight;
    }

    public Boolean hasUserNo(Long userNo){
        return this.userNo.equals(userNo);
    }
}
