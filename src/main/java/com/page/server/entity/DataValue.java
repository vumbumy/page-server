package com.page.server.entity;

import com.sun.istack.NotNull;
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
    @JoinColumn(name = "type_no")
    @NotNull
    private DataType type;

    @NotNull
    private Long contentNo;

    private String value;
}
