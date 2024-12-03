package com.pakesz.central.entity;

import lombok.Getter;

@Getter
public enum ERole {
    ADMIN("ADMIN");

    private final String name;

    ERole(String name) {
        this.name = name;
    }

    public String getNameWithPrefix() {
        return "ROLE_" + name;
    }
}
