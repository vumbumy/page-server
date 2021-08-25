package com.page.server.entity.base;

import com.page.server.constant.AccessRight;
import com.page.server.entity.Permission;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@NoArgsConstructor
@MappedSuperclass
public class BaseContent extends BaseTimeEntity{
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

    public Long managerNo;

    public Boolean shared;

    public Boolean deleted;

    @Builder(access = AccessLevel.PRIVATE)
    public BaseContent(Timestamp createdAt, Timestamp updatedAt, Long contentNo, String contentName, List<Permission> permissions, Long managerNo, Boolean shared, Boolean deleted) {
        super(createdAt, updatedAt);
        this.contentNo = contentNo;
        this.contentName = contentName;
        this.permissions = permissions;
        this.managerNo = managerNo;
        this.shared = shared;
        this.deleted = deleted;
    }

    public boolean isManager (Long userNo) {
        return managerNo != null && managerNo.equals(userNo);
    }

    public boolean isReadable (Long userNo, Long groupNo) {
        return this.shared || this.isManager(userNo) || this.permissions.stream()
                .anyMatch(permission -> permission.hasUserNo(userNo) || permission.hasGroupNo(groupNo));
    }

    public boolean isWriteable (Long userNo, Long groupNo) {
        return this.isManager(userNo) || this.permissions.stream()
                .anyMatch(permission -> (permission.hasUserNo(userNo) || permission.hasGroupNo(groupNo)) && permission.accessRight.equals(AccessRight.WRITE));
    }
}
