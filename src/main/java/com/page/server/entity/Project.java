package com.page.server.entity;

import com.page.server.entity.base.BaseContent;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@NoArgsConstructor
@Entity
@Table(name = "_PROJECT")
public class Project extends BaseContent {

    public String description;

    public Timestamp startedAt;

    public Timestamp endedAt;

    @OneToMany(fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    @JoinTable(
            name = "_PROJECT_TYPES",
            joinColumns = @JoinColumn(name = "PROJECT_NO"),
            inverseJoinColumns = @JoinColumn(name = "TYPE_NO"))
    List<DataType> types;
}
