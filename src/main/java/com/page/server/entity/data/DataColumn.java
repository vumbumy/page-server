package com.page.server.entity.data;

import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@NoArgsConstructor
@Entity
@Table(name = "_DATA_COLUMN")
public class DataColumn implements Serializable {
    private static final long serialVersionUID = 1L;

    public enum Type {
        Text, TextArea, Number, Date, URL
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long columnNo;

    @NotEmpty
    public String columnName;

    @NotNull
    public Type columnType;

    public String defaultValue;

    public Boolean required = false;
    public Boolean deleted = false;
}
