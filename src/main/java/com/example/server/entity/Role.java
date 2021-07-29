package com.example.server.entity;

import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "_ROLE")
@NoArgsConstructor
public class Role {
    public static final String ADMIN = "ADMIN";
    public static final String USER = "USER";
    public static final String PARTNER = "PARTNER";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long roleNo;

    @Column(nullable = false)
    public String value;
}
