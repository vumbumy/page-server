package com.page.server.entity;

import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@NoArgsConstructor
@Entity
@Table(name = "_TYPE")
public class Type implements Serializable {
    private static final long serialVersionUID = 1L;

    public enum DataType {
        String, Number
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long typeNo;

    @NotEmpty
    public String typeName;

    @NotNull
    public DataType dataType;

    public String defaultValue;

    public Boolean required = false;
    public Boolean deleted = false;
}
