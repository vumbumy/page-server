package com.page.server.model.form;

import com.page.server.model.form.base.Condition;

import java.util.List;

public class UpdateParam {
    public Long project;

    public List<Condition> conditions;

    public Long column;

    public String value;

    @Override
    public String toString() {
        return "UpdateParam{" +
                "project=" + project +
                ", conditions=" + conditions +
                ", column=" + column +
                ", value='" + value + '\'' +
                '}';
    }
}
