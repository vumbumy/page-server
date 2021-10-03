package com.page.server.entity;

import com.page.server.constant.AccessRight;
import com.page.server.entity.base.BaseTimeEntity;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "_USER_GROUP")
@NoArgsConstructor
public class UserGroup extends BaseTimeEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long groupNo;

    public String groupName;

    @OneToMany(fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    @JoinTable(
            name = "_USER_GROUP_PERMISSIONS",
            joinColumns = @JoinColumn(name = "GROUP_NO"),
            inverseJoinColumns = @JoinColumn(name = "PERMISSION_NO"))
    public List<Permission> permissions;

    @Builder
    public UserGroup(Long groupNo, String groupName, List<User> users) {
        this.groupNo = groupNo;
        this.groupName = groupName;
    }

    public boolean isReadable (Long userNo, List<Long> groupNo) {
        return this.permissions != null && this.permissions.stream()
                .anyMatch(permission -> permission.isUserNo(userNo)
                        || (groupNo != null && groupNo.contains(groupNo))
                        || permission.isPublic());
    }

    public boolean isWritable(Long userNo, List<Long> groupNo) {
        return this.permissions != null && this.permissions.stream()
                .anyMatch(permission -> (permission.isUserNo(userNo)
                        || (groupNo != null && groupNo.contains(groupNo))
                        || permission.isPublic())
                        && permission.accessRight.equals(AccessRight.WRITE));
    }
}
