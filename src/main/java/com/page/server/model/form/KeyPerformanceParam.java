package com.page.server.model.form;

import com.page.server.model.form.base.Condition;

import java.util.List;

public class KeyPerformanceParam {
    public Long project;

    public List<Condition> conditions;

    public Integer value;

    @Override
    public String toString() {
        return "KeyPerformanceParam{" +
                "project=" + project +
                ", conditions=" + conditions +
                ", value='" + value + '\'' +
                '}';
    }
}
