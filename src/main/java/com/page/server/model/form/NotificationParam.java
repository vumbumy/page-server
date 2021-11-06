package com.page.server.model.form;

import com.page.server.model.form.base.Condition;

import java.util.List;

public class NotificationParam {
    public Long project;

    public List<Condition> conditions;

    @Override
    public String toString() {
        return "NotificationParam{" +
                "project=" + project +
                ", conditions=" + conditions +
                '}';
    }
}
