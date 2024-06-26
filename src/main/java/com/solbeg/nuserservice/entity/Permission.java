package com.solbeg.nuserservice.entity;

import lombok.Getter;

@Getter
public enum Permission {
    NEWS_READ("news:read"),
    NEWS_WRITE("news:write"),
    COMMENTS_READ("comments:read"),
    COMMENTS_WRITE("comments:write");

    private final String permission;

    Permission(String permission) {
        this.permission = permission;
    }
}
