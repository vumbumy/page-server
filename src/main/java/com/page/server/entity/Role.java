package com.page.server.entity;

import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "_ROLE")
@NoArgsConstructor
public class Role implements Serializable {
    public static final String PREFIX = "ROLE_";
    public static final String ADMIN = "ADMIN";
    public static final String USER = "USER";
    public static final String PARTNER = "PARTNER";

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roleNo;

    @Column(nullable = false)
    public String value;
}
