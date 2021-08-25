package com.page.server.entity;

import com.page.server.entity.base.BaseContent;
import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.util.List;

@NoArgsConstructor
@Entity
@Table(name = "_TICKET")
public class Ticket extends BaseContent {

    public enum Status {
        TODO, PROGRESS, REVIEW, DONE
    }

    @Builder
    public Ticket(Timestamp createdAt, Timestamp updatedAt, Long contentNo, String contentName, List<Permission> permissions, Long managerNo, Boolean shared, Boolean deleted, Long projectNo, Status status) {
        super(createdAt, updatedAt, contentNo, contentName, permissions, managerNo, shared, deleted);
        this.projectNo = projectNo;
        this.status = status;
    }

    @NotNull
    public Long projectNo;

    @NotNull
    public Status status;
}
