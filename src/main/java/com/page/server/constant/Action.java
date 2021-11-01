package com.page.server.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Action {
    NOTIFICATION("NOTIFICATION", "{}"),
    UPDATE("UPDATE", "{}");

    private final String name;
    private final String form;
}
