package com.page.server.entity;

import com.page.server.entity.base.BaseContent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

@NoArgsConstructor
@Entity
@Table(name = "_EVENT")
public class Event extends BaseContent {

    @Getter
    @AllArgsConstructor
    public enum Type {
        NOTIFICATION("NOTIFICATION", "{}"),
        UPDATE("UPDATE", "{}"),
        KPI("KPI", "{}");

        private final String name;
        private final String form;
    }

    public Type eventType;

    public String paramJson;

    public Boolean scheduled;
}
