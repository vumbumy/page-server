package com.example.server.entity;

import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "_ROLE")
@NoArgsConstructor
public class Role {
    public static String ADMIN = "ADMIN";
    public static String USER = "USER";
    public static String PARTNER = "PARTNER";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long roleNo;

    @Column(nullable = false)
    public String value;
}
