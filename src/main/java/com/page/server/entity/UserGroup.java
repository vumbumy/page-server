package com.page.server.entity;

import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "_USER_GROUP")
@NoArgsConstructor
public class UserGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long groupNo;

    public String groupName;

    @Builder
    public UserGroup(Long groupNo, String groupName) {
        this.groupNo = groupNo;
        this.groupName = groupName;
    }
}
