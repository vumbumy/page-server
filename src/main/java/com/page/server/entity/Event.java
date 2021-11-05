package com.page.server.entity;

import com.page.server.constant.Action;
import com.page.server.entity.base.BaseContent;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

@NoArgsConstructor
@Entity
@Table(name = "_EVENT")
public class Event extends BaseContent {

    public enum Type {
        SCHEDULE, KPI
    }

    public Action action;

    public String paramJson;

    public Type eventType;

    public Boolean enabled;
}
