package com.example.server.entity.base;

import com.example.server.entity.Permission;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@MappedSuperclass
public class BaseContent {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long contentNo;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "_CONTENT_PERMISSIONS",
            joinColumns = @JoinColumn(name = "CONTENT_NO"),
            inverseJoinColumns = @JoinColumn(name = "PERMISSION_NO"))
    public List<Permission> permissions;

    @Builder(access = AccessLevel.PRIVATE)
    public BaseContent(Long contentNo, List<Permission> permissions) {
        this.contentNo = contentNo;
        this.permissions = permissions;
    }

    public boolean isMatch (Long userNo) {
        return this.permissions.stream().anyMatch(permission -> permission.userNo.equals(userNo));
    }
}
