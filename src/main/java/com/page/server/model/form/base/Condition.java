package com.page.server.model.form.base;

public class Condition {
    public enum Operator {
        gt, lt, ge, le, eq, ne
    }

    public Long column;

    public Operator op;

    public String value;

    @Override
    public String toString() {
        return "Condition{" +
                "column=" + column +
                ", op=" + op +
                ", value='" + value + '\'' +
                '}';
    }
}
