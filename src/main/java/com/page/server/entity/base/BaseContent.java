package com.page.server.entity.base;

import com.page.server.constant.AccessRight;
import com.page.server.entity.Permission;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@NoArgsConstructor
@MappedSuperclass
public class BaseContent extends BaseTimeEntity {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long contentNo;

    public String contentName;

    @OneToMany(fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    @JoinTable(
            name = "_CONTENT_PERMISSIONS",
            joinColumns = @JoinColumn(name = "CONTENT_NO"),
            inverseJoinColumns = @JoinColumn(name = "PERMISSION_NO"))
    public List<Permission> permissions;

    @NotNull
    public Long managerNo;

    public Boolean readable = false;
    public Boolean writeable = false;
    public Boolean deleted = false;

    @Builder(access = AccessLevel.PRIVATE)
    public BaseContent(Long contentNo, String contentName, List<Permission> permissions, @NotNull Long managerNo, Boolean readable, Boolean writeable, Boolean deleted) {
        this.contentNo = contentNo;
        this.contentName = contentName;
        this.permissions = permissions;
        this.managerNo = managerNo;
        this.readable = readable;
        this.writeable = writeable;
        this.deleted = deleted;
    }

    public boolean isManager (Long userNo) {
        return managerNo.equals(userNo);
    }

    public boolean isReadable (Long userNo, Long groupNo) {
        return this.readable || this.isManager(userNo) || this.permissions.stream()
                .anyMatch(permission -> permission.hasUserNo(userNo) || permission.hasGroupNo(groupNo));
    }

    public boolean iswritable(Long userNo, Long groupNo) {
        return this.writeable || this.isManager(userNo) || this.permissions.stream()
                .anyMatch(permission -> (permission.hasUserNo(userNo) || permission.hasGroupNo(groupNo)) && permission.accessRight.equals(AccessRight.WRITE));
    }
}
