package com.page.server.model.form;

public class Condition {
    public enum Operator {
        GT, LT, GE, LE, EQ, NE
    }

    Long type;

    Operator op;

    String value;
}
