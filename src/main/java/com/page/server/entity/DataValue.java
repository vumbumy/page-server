package com.page.server.entity;

import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@NoArgsConstructor
@Entity
@Table(name = "_VALUE")
public class DataValue implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long valueNo;

    @OneToOne(fetch = FetchType.EAGER)
    private DataType type;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CONTENT_NO")
    private Ticket ticket;

    private String value;
}
