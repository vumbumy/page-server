package com.page.server.entity;

import com.page.server.entity.base.BaseTimeEntity;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "_USER_GROUP")
@NoArgsConstructor
public class UserGroup extends BaseTimeEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long groupNo;

    public String groupName;

    @Builder
    public UserGroup(Long groupNo, String groupName) {
        this.groupNo = groupNo;
        this.groupName = groupName;
    }
}
