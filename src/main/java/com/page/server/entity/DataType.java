package com.page.server.entity;

import com.sun.istack.NotNull;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@NoArgsConstructor
@Entity
@Table(name = "_TYPE")
public class DataType implements Serializable {
    private static final long serialVersionUID = 1L;

    public enum Type {
        String, Number
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long typeNo;

    @NotNull
    Type value;

    @NotNull
    Boolean required;
}
