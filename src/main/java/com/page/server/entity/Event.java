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

    @AllArgsConstructor
    public enum Type {
        NOTIFICATION("NOTIFICATION", "{}"),
        UPDATE("UPDATE", "{}");

        public final String name;
        public final String form;
    }

    public Type eventType;

    public String paramJson;

    public Boolean scheduled;
}
