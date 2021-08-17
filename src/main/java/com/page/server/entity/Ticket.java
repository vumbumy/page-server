package com.page.server.entity;

import com.page.server.entity.base.BaseContent;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
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
    public Ticket(Timestamp createdAt, Timestamp updatedAt, Long contentNo, List<Permission> permissions, Long managerNo, Boolean isPublic, Boolean deleted, String title, String content, Status status) {
        super(createdAt, updatedAt, contentNo, permissions, managerNo, isPublic, deleted);
        this.title = title;
        this.content = content;
        this.status = status;
    }

    @Column
    public String title;

    @Column
    public String content;

    @Column
    public Status status;
}
