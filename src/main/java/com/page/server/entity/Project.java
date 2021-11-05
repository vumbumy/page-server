package com.page.server.entity;

import com.page.server.entity.base.BaseContent;
import com.page.server.entity.data.DataColumn;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@NoArgsConstructor
@Entity
@Table(name = "_PROJECT")
public class Project extends BaseContent {

    public String description;
    public Long startedAt;
    public Long endedAt;

    @ManyToMany(fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    @JoinTable(
            name = "_PROJECT_COLUMNS",
            joinColumns = @JoinColumn(name = "PROJECT_NO"),
            inverseJoinColumns = @JoinColumn(name = "COLUMN_NO"))
    public List<DataColumn> columns;

    @Builder
    public Project(Long contentNo, String contentName, List<Permission> permissions, @NotNull Long managerNo, Boolean deleted, String description, Long startedAt, Long endedAt, List<DataColumn> columns) {
        super(contentNo, contentName, permissions, managerNo, deleted);
        this.description = description;
        this.startedAt = startedAt;
        this.endedAt = endedAt;
        this.columns = columns;
    }
}
