package com.example.server.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "_TICKET")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long ticketNo;

    String content;

    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "_TICKET_PERMISSIONS",
            joinColumns = @JoinColumn(name = "USER_NO"),
            inverseJoinColumns = @JoinColumn(name = "PERMISSION_NO"))
    List<Permission> permissions;
}
