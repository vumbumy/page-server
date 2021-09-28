package com.page.server.entity;

import com.page.server.entity.base.BaseTimeEntity;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Entity
@Table(name = "_PROJECT_TYPE_REF")
public class ProjectTypeRef extends BaseTimeEntity {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long projectTypeRefNo;

    public Long projectNo;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "type_no")
    @NotNull
    public Type type;

    public Boolean deleted = false;

    @Builder
    public ProjectTypeRef(Long projectTypeRefNo, Long projectNo, @NotNull Type type, Boolean deleted) {
        this.projectTypeRefNo = projectTypeRefNo;
        this.projectNo = projectNo;
        this.type = type;
        this.deleted = deleted;
    }
}
