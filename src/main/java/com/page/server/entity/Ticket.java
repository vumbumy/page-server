package com.page.server.entity;

import com.page.server.entity.base.BaseContent;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@Entity
@Table(name = "_TICKET")
public class Ticket extends BaseContent {

    public enum Status {
        TODO, PROGRESS, REVIEW, DONE
    }

    @Builder
    public Ticket(Long contentNo, List<Permission> permissions, String title, String content, Status status) {
        super(contentNo, permissions);
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

    @Column
    public Boolean isPublic;
}
