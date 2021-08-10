package com.example.server.entity;

import com.example.server.entity.base.BaseContent;
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
    public Ticket(Long contentNo, List<Permission> permissions, String content, Status status) {
        super(contentNo, permissions);
        this.content = content;
        this.status = status;
    }

//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    public Long ticketNo;

    @Column
    public String content;

    @Column
    public Status status;


}
