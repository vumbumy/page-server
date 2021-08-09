package com.example.server.entity;

import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "_TICKET")
@NoArgsConstructor
public class Ticket {

    public enum Status {
        TODO, PROGRESS, REVIEW, DONE
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long ticketNo;

    @Column
    public String content;

    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "_TICKET_PERMISSIONS",
            joinColumns = @JoinColumn(name = "TICKET_NO"),
            inverseJoinColumns = @JoinColumn(name = "PERMISSION_NO"))
    public List<Permission> permissions;

    @Column
    public Status status;


    @Builder
    public Ticket(Long ticketNo, String content, List<Permission> permissions, Status status) {
        this.ticketNo = ticketNo;
        this.content = content;
        this.permissions = permissions;
        this.status = status;
    }

    public boolean isMatch (Long userNo) {
        return this.permissions.stream().anyMatch(permission -> permission.userNo.equals(userNo));
    }
}
