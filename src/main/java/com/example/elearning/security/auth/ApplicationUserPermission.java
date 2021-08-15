package com.example.elearning.security.auth;

public enum ApplicationUserPermission {

    USER_READ("user:read"),
    USER_WRITE("user:write"),
    RESOURCE_READ("resource:read"),
    RESOURCE_WRITE("resource:write");

    private final String permission;

    ApplicationUserPermission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
