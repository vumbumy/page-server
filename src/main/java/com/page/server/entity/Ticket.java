package com.page.server.entity;

import com.page.server.entity.base.BaseContent;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.List;

@NoArgsConstructor
@Entity
@Table(name = "_TICKET")
public class Ticket extends BaseContent {

    public enum Status {
        TODO, PROGRESS, REVIEW, DONE
    }

    @Builder
    public Ticket(Long contentNo, String contentName, List<Permission> permissions, @NotNull Long managerNo, Boolean shared, Boolean deleted, @NotNull Long projectNo, @NotNull Status status) {
        super(contentNo, contentName, permissions, managerNo, shared, deleted);
        this.projectNo = projectNo;
        this.status = status;
    }

    @NotNull
    public Long projectNo;

    @NotNull
    public Status status;
}
